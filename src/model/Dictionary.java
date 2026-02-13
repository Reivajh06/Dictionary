package model;

public class Dictionary<K, V> {

	private static final int BASELENGTH = 8;
	private static Object DUMMY = new Object();

	private Object[] hashtable;

	public Dictionary() {
		hashtable = new Object[BASELENGTH];
	}

	public void add(K key, V value) {
		int index = key.hashCode() % hashtable.length;

		if(hashtable[index] == null || hashtable[index].equals(DUMMY)) {
			hashtable[index] = value;
			return;
		}


	}
}
