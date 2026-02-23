package model;

import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Stream;

@SuppressWarnings("all")
public class PyDict<K, V> {

	private static final int EXPANSIONFACTOR = 66;

	private static final int DKIXEMPTY = -1;
	private static final int DKIXDUMMY = -2;
	private static final int PERTURBSHIFT = 5;
	private static final int BASELENGTH = 8;

	private int[] indices;
	private int mask;

	private Entry<K, V>[] entries;
	private int nextEntryIndex = 0;

	private int nItems;
	private int nDummies;

	public static <K, V> PyDict<K, V> fromKeys(K[] keys, V initialValue) {
		return new PyDict<>(keys, initialValue);
	}

	public static <K, V> PyDict<K, V> fromKeys(K[] keys) {
		return fromKeys(keys, null);
	}

	@SuppressWarnings("all")
	public static <K, V> PyDict<K, V> of(Object... args) {
		if(args.length % 2 != 0) throw new IllegalArgumentException("Args must be key, value pairs");

		PyDict raw = new PyDict();

		for(int i = 0; i < args.length; i += 2) {
			raw.put(args[i], args[i + 1]);
		}

		return raw;
	}

	public PyDict() {
		indices = new int[BASELENGTH];
		fill(indices, DKIXEMPTY);
		entries = new Entry[BASELENGTH];
		mask = BASELENGTH - 1;

		nItems = 0;
	}

	public PyDict(K[] keys, V[] values) {
		if(keys.length != values.length) throw new IllegalArgumentException("keys and values arrays lengths do not match");

		indices = new int[keys.length * 2];
		fill(indices, DKIXEMPTY);
		entries = new Entry[keys.length * 2];
		mask = indices.length - 1;
		nItems = 0;

		for(int i = 0; i < keys.length; i++) {
			put(keys[i], values[i]);
		}
	}

	/*
	* Constructor similar to fromkeys function in python to create dictionary with default value
	*
	* */
	public PyDict(K[] keys, V initialValue) {
		indices = new int[keys.length * 2];
		fill(indices, -1);
		entries = new Entry[keys.length * 2];

		mask = indices.length - 1;

		nItems = 0;

		for (K key : keys) {
			put(key, initialValue);
		}
	}

	public PyDict(K[] keys){
		this(keys, (V[]) new Object());
	}

	private void grow() {
		Entry<K, V>[] entr = new Entry[entries.length];
		System.arraycopy(entries, 0, entr, 0, entries.length);

		entries = new Entry[entries.length * 2];
		indices = new int[indices.length * 2];

		fill(indices, -1);

		mask = indices.length - 1;
		nItems = 0;
		nextEntryIndex = 0;

		for(Entry<K, V> entry : entr) {
			if(entry == null) continue;
			put(entry.key, entry.value);
		}
	}

	private void fill(int[] arr, int initialValue) {
		for(int i = 0; i < arr.length; i++) {
			arr[i] = initialValue;
		}
	}

	public void clear() {
		entries = new Entry[entries.length];
		indices = new int[indices.length];
		fill(indices, DKIXEMPTY);
		nItems = 0;
		nextEntryIndex = 0;
		nDummies = 0;
	}

	public PyDict<K, V> copy() {
		PyDict<K, V> copyDict = new PyDict<>();

		for(Entry<K, V> e : entries) {
			if(e == null) continue;
			copyDict.put(e.key, e.value);
		}

		return copyDict;
	}

	public void put(K key, V value) {
		if((nItems * 100) / indices.length >= EXPANSIONFACTOR) {
			grow();
		}

		int firstDummyPos = -1;

		int perturb = key.hashCode();
		int j = perturb;
		int index = j & mask;

		while(true) {
			if(firstDummyPos == -1) {
				if(indices[index] == DKIXDUMMY) firstDummyPos = index;

				else if(indices[index] == DKIXEMPTY) break;

			} else {
				if(indices[index] == DKIXEMPTY) {
					index = firstDummyPos;
					nDummies--;
					break;
				}
			}

			if(indices[index] != DKIXDUMMY && entries[indices[index]] != null && entries[indices[index]].key.equals(key)) {
				entries[indices[index]] = new Entry<>(key, value, key.hashCode());
				return;
			}

			//unsigned shift
			perturb >>>= PERTURBSHIFT;
			j = (5 * j) + 1 + perturb;
			index = j & mask;
		}

		entries[nextEntryIndex] = new Entry<>(key, value, key.hashCode());
		indices[index] = nextEntryIndex++;
		nItems++;
	}

	public V get(K key) {
		if(nItems == 0) return null;

		int perturb = key.hashCode();
		int j = perturb;
		int index = j & mask;

		while(true) {
			if(indices[index] == DKIXEMPTY) return null;

			if(indices[index] != DKIXDUMMY && entries[indices[index]] != null && entries[indices[index]].key.equals(key)) {
				return entries[indices[index]].value;
			}

			perturb >>>= PERTURBSHIFT;
			j = (5 * j) + 1 + perturb;
			index = j & mask;
		}
	}

	public int size() {
		return nItems;
	}

	public V pop(K key) {
		if(nItems == 0) throw new IllegalArgumentException("Key %s not found".formatted(key));

		int perturb = key.hashCode();
		int j = perturb;
		int index = j & mask;

		while(true) {
			if(indices[index] == DKIXEMPTY) throw new IllegalArgumentException("Key %s not found".formatted(key));

			if(indices[index] >= 0 && entries[indices[index]] != null && entries[indices[index]].key.equals(key)) {
				break;
			}

			perturb >>>= PERTURBSHIFT;
			j = (5 * j) + 1 + perturb;
			index = j & mask;
		}

		V val = entries[indices[index]].value;

		entries[indices[index]] = null;
		indices[index] = DKIXDUMMY;

		nItems--;
		nDummies++;

		rebuild();

		return val;
	}

	public Object pop(K key, Object defaultValue) {
		if(nItems == 0) return defaultValue;

		int perturb = key.hashCode();
		int j = perturb;
		int index = j & mask;

		while(true) {
			if(indices[index] == DKIXEMPTY) return defaultValue;

			if(indices[index] >= 0 && entries[indices[index]] != null && entries[indices[index]].key.equals(key)) {
				break;
			}

			perturb >>>= PERTURBSHIFT;
			j = (5 * j) + 1 + perturb;
			index = j & mask;
		}

		V val = entries[indices[index]].value;

		entries[indices[index]] = null;
		indices[index] = DKIXDUMMY;

		nItems--;
		nDummies++;

		rebuild();

		return val;
	}

	private void rebuild() {
		if(nDummies > nItems) {
			Entry<K, V>[] entriesBuffer = entries;

			clear();

			for(Entry<K, V> e : entriesBuffer) {
				if(e == null) continue;

				put(e.key, e.value);
			}
		}
	}

	public Pair popItem() {
		if(nItems == 0) throw new IndexOutOfBoundsException("PyDict is empty!");

		int i = nextEntryIndex - 1;

		Entry e = entries[i];

		int perturb = entries[i].hash;
		int j = perturb;
		int index = j & mask;

		while(i >= 0 && entries[i] == null) {
			i--;
		}

		if(i < 0) throw new IndexOutOfBoundsException("PyDict is empty");

		Pair p = new Pair(entries[i].key, entries[i].value);
		pop(entries[i].key);

		return p;
	}

	public void update(PyDict<K, V> otherDict) {
		for(Pair<K, V> pair : otherDict.items()) {
			put(pair.key, pair.value);
		}
	}

	public V setDefault(K key, V defaultValue) {
		if(get(key) == null) put(key, defaultValue);

		return get(key);
	}

	public Object[] keys() {
		return Stream.of(entries)
				.filter(Objects::nonNull)
				.map(Entry::key)
				.toArray();
	}

	public K[] keys(IntFunction<K[]> arrayProvider) {
		//Method to return the keys as their intended class specified when the object PyDict was created
		//Basically imitates the use of the toArray function used in the List interface
		//Provided by an IntFunction<generic> which identifies the desired class to return (Example, keys(String[]::new))
		return Stream.of(entries)
				.filter(Objects::nonNull)
				.map(Entry::key)
				.toArray(arrayProvider);
	}

	public Object[] values() {
		return Stream.of(entries)
				.filter(Objects::nonNull)
				.map(Entry::value)
				.toArray(Object[]::new);
	}

	public V[] values(IntFunction<V[]> arrayProvider) {
		return Stream.of(entries)
				.filter(Objects::nonNull)
				.map(Entry::value)
				.toArray(arrayProvider);
	}


	public Pair<K, V>[] items() {
		return (Pair<K, V>[]) Stream.of(entries)
				.filter(Objects::nonNull)
				.map(e -> new Pair<>(e.key, e.value))
				.toArray(Pair[]::new);
	}

	private record Entry<K, V>(K key, V value, int hash) {
	}

	public record Pair<K, V>(K key, V value) {

		public String toString() {
			return "[%s, %s]".formatted(key, value);
		}
	}
}
