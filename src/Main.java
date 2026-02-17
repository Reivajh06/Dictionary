import model.PyDict;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		PyDict<String, Integer> dict1 = PyDict.fromKeys(new String[]{"Hello", "World"});
		System.out.println(Arrays.toString(dict1.items()));
	}
}
