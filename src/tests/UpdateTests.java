package tests;
import model.PyDict;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class UpdateTests contains some tests to check the PyDict method
 * public void update(PyDict<K,V> otherDict)
 */
public class UpdateTests{
    PyDict<String, Integer> dict;
    PyDict<String, Integer> dict2;
    
    /**
     * Initializing dictionaries dict and dict2 before the tests
     */
    @Before
    public void init(){
        dict = new PyDict<>();
        dict2 = new PyDict<>();
    }
    
    /**
     * Test to update an empty dictionary with the items of another
     * empty dictionary. The first empty dictionary, which is the updated one,
     * must be empty
     */
    @Test
    public void UpdateEmptyDictEmptyItems(){
        dict.update(dict2);
        assertEquals("[]", Arrays.toString(dict.items()));
    }
    
    /**
     * Test to update an empty dictionary with the items of a not empty
     * dictionary. The empty dictionary, which is the updated one, must have
     * the other dictionary's items
     */
    @Test
    public void UpdateEmptyDictNotEmptyItems(){
        dict2 = dict2.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        dict.update(dict2);
        assertEquals("[[A, 1], [B, 2], [C, 3]]", Arrays.toString(dict.items()));
    }
    
    /**
     * Test to update a not empty dictionary with the items of
     * an empty dictionary. The updated dictionary has the same items
     * as the original
     */
    @Test
    public void UpdateNotEmptyDictEmptyItems(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        dict.update(dict2);
        assertEquals("[[A, 1], [B, 2], [C, 3]]", Arrays.toString(dict.items()));
    }
    
    /**
     * Test to update a not empty dictionary with the items of another
     * not empty dictionary. The updated dictionary has not only its original
     * elements but also the other dictionary's elements
     */
    @Test
    public void UpdateNotEmptyDictNotEmptyItems(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        dict2 = dict2.of(
            "D", 4,
            "E", 5
        );
        
        dict.update(dict2);
        assertEquals("[[A, 1], [B, 2], [C, 3], [D, 4], [E, 5]]", 
                     Arrays.toString(dict.items()));
    }
}