package tests;
import model.PyDict;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class Pop2Tests contains some tests to check the PyDict method
 * public Object pop(K key, Object defaultValue)
 */
public class Pop2Tests{
    PyDict<String, Integer> dict;

    /**
     * Initializing dictionary dict before the tests
     */
    @Before
    public void init(){
        dict = new PyDict<>();
    }


    /**
     * Test to pop an item with a null key from an empty dictionary.
     * It returns the default value assiged to the key, null in this case
     */
    @Test
    public void popNullKeyEmptyDict(){
        Object pop_value = dict.pop(null, null);
        assertEquals(null, pop_value);
    }

    /**
     * Test to pop an item with a not null key from an empty dictionary.
     * It returns the default value assigned to the key, "" in this case
     */
    @Test
    public void popNotNullKeyEmptyDict(){
        Object pop_value = dict.pop("A", "");
        assertEquals("", pop_value);
    }

    /**
     * Test to pop an item with a null key from a not empy dictionary.
     * It generates a NullPointerExcetpion because a null key is not alowed
     * in the dictionary
     */
    @Test(expected = NullPointerException.class)
    public void popNullKeyNotEmpyDict(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        dict.pop(null, null);
    }

    /**
     * Test to pop an item that not belongs to the dictionary.
     * It returns the default value assigned to the key, "" in this case
     */
    @Test
    public void popNotNullNotValidKeyNotEmpyDict(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        Object pop_value = dict.pop("D", "");
        assertEquals("", pop_value);
    }

    /**
     * Test to pop an item that belongs to the dictionary.
     * It pops the item (key, value) from the dictionary and returns the value
     */
    @Test
    public void popNotNullValidKeyNotEmptyDict(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        Object pop_value = dict.pop("A", "");
        assertEquals(1, pop_value);
    }
    
    /**
     * Test to pop an item that belongs to the dictionary.
     * It pops the item (key, value) from the dictionary and returns the value
     */
    @Test
    public void popNotNullValidKeyNotEmptyDict2(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        dict.pop("A", "");
        assertEquals("[[B, 2], [C, 3]]", Arrays.toString(dict.items()));
    }
}