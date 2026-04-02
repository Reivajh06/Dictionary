package tests;
import model.PyDict;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class ContainsKeyTests contains some tests to check the PyDict method
 * public boolean containsKey(K key)
 */
public class ContainsKeyTests{
    
    PyDict<String, Integer> dict;

    /**
     * Initializing dictionary dict before the tests
     */
    @Before
    public void init(){
        dict = new PyDict<>();
    }
    
    /**
     * Test to check if an empty dictionary contains a null key. The
     * PyDict method containsKey() must return false
     */
    @Test
    public void ContainsNullKeyEmptyDict(){
        boolean contains_key = dict.containsKey(null);
        assertEquals(false, contains_key);
    }
    
    /**
     * Test to check if a not empty dictionary contains a null key.
     * It must raise a NullPointerException, because a key cannot be null
     */
    @Test(expected = NullPointerException.class)
    public void ContainsNullKeyNotEmptyDict(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        boolean contains_key = dict.containsKey(null);
    }
    
    /**
     * Test to check if an empty dictionary contains a not null key.
     * The PyDict method containsKey() must return false
     */
    @Test
    public void ContainsNotNullKeyEmptyDict(){
        boolean contains_key = dict.containsKey("A");
        assertEquals(false, contains_key);
    }
    
    /**
     * Test to check if a not empty dictionary contains a not null key that
     * not belongs to the dictionary. The PyDict method containsKey() must
     * return false
     */
    @Test
    public void ContainsNotNullKeyNotEmptyDict1(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        boolean contains_key = dict.containsKey("D");
        assertEquals(false, contains_key);
    }
    
    /**
     * Test to ckeck if a not empty dictionary contains a not null key that
     * belongs to the dictionary. The PyDict method containsKey() must
     * return true
     */
    @Test
    public void ContainsNotNullKeyNotEmptyDict2(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        boolean contains_key = dict.containsKey("A");
        assertEquals(true, contains_key);
    }
}