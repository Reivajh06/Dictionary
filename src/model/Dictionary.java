package model;
//a
public class Dictionary<K, V> {

	private static final int BASELENGTH = 8;
	private static Object DUMMY = new Object();

	private Object[] hashtable;

	public Dictionary() {
		hashtable = new Object[BASELENGTH];
	}

	public void add(K key, V value) {

	}
}
