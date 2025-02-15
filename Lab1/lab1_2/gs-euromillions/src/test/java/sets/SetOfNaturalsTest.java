/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 *
 * @author ico0
 */
public class SetOfNaturalsTest {
    
    private SetOfNaturals set;

    @BeforeEach
    public void setUp() {        
        set = new SetOfNaturals();
    }
    
    @AfterEach
    public void tearDown() {
    	set = null;
    }
     
    @Test  
    public void testNegatives() {         
        // must fail with exception
        assertThrows( IllegalArgumentException.class, ()->set.add( -1));
    }
    
    
    @Test
	public void testAddInt() {
    	int elem = 10; 
    	set.add(10);
    	assertTrue(  set.contains( elem));		
	}

    @Test 
	public void testAddIntArray() {
		int [] elems = new int[]{ 10, 20, -30};
			// must fail with exception 
            assertThrows( IllegalArgumentException.class, ()->set.add( elems ));
	}
    
    
}
