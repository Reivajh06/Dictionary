package tests;
import model.PyDict;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class PopItemTests contains some tests to check the PyDict method
 * public  Pair popItem()
 */
public class PopItemTests{
    PyDict<String, Integer> dict;
    
    /**
     * Initializing dictionary dict before the tests
     */
    @Before
    public void init(){
        dict = new PyDict<>();
    }


    /**
     * Test to pop an item from an empty dictionary. It raises
     * an IndexOutOfBoundsException
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void popItemEmptyDict(){
        dict.popItem();
    }

    /**
     * Test to pop an item from a not empty dictionary. It checks if the
     * method popItem() returns the removed item (key/value)
     */
    @Test
    public void popItemNotEmptyDict(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        Object item = dict.popItem();
        assertEquals("[C, 3]", item.toString());
    }

    /**
     * Test to pop an item from a not empty dictionary. It checks if the
     * method popItem() removes the last-inserted item (key/value)
     */
    @Test
    public void popItemNotEmptyDict2(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        dict.popItem();
        assertEquals("[[A, 1], [B, 2]]", Arrays.toString(dict.items()));
    }
}