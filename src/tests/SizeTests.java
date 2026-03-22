package tests;
import model.PyDict;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class SizeTests contains some tests to check the PyDict method
 * public int size()
 */
public class SizeTests{
    PyDict<String, Integer> dict;

    /**
     * Initializing the dictionary dict before the tests
     */
    @Before
    public void init(){
        dict = new PyDict<>();
    }


    /**
     * Test to get the size of an empty dictionary. The dictionary method size()
     * returns 0
     */
    @Test
    public void sizeEmptyDict(){
        int size = dict.size();
        assertEquals(0, size);
    }

    /**
     * Test to get the size of a not empty dictionary. The dictionary method
     * size() returns the number of items (key, value) of the dictionary
     */
    @Test
    public void sizeNoEmptyDict(){
        dict = dict.of(
            "A", 1,
            "B", 2,
            "C", 3
        );
        
        int size = dict.size();
        assertEquals(3, size);
    }
}
