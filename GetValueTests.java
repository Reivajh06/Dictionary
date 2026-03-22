package tests;
import model.PyDict;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class GetValueTests contains some tests to check the PyDict method
 * public V get(K key)
 */
public class GetValueTests{
    PyDict<String, Integer> dict;

    /**
     * Initializing the dictionary dict before the tests
     */
    @Before
    public void init(){
        dict = new PyDict<>();
    }

    /**
     * Test to get the value of a null key from an empty dictionary.
     * The dictionary method get() returns null because there are not items
     * in the dictionary
     */
    @Test
    public void testGetNoKeyEmptyDict(){
        Object value = dict.get(null);
        assertEquals(null, value);
    }

    /**
     * Test to get the value of a not null key from an empty dictionary.
     * The dictionary method get() returns null because there are not items
     * in the dictionary
     */
    @Test
    public void testGetKeyEmptyDict(){
        Object value = dict.get("A");
        assertEquals(null, value);
    }

    /**
     * Test to get the value of a null key from a not empty dictionary.
     * It generates a NullPointerException, because a null key is not allowed
     * in the dictionary
     */
    @Test(expected = NullPointerException.class)
    public void testGetNoKey(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        Object value = dict.get(null);
    }

    /**
     * Test to get the value of a not null key that not belongs to the
     * dictionary. In this case the dictionary method get() returns null.
     */
    @Test
    public void testGetWrongKey(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        Object value = dict.get("D");
        assertEquals(null, value);
    }

    /**
     * Test to get the value of a not null key that belongs to the dictionary.
     * In this case the dictionary method get() returns the value assigned to
     * that key.
     */
    @Test
    public void testGetCorrectKey(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        Object value = dict.get("A");
        assertEquals(1, value);
    }
}