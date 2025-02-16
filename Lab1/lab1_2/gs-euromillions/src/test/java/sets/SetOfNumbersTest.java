package sets;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class SetOfNumbersTest {
	private SetOfNumbers setA;
	private SetOfNumbers setB;
	private SetOfNumbers setC;
        private SetOfNumbers setD;
	
	/**
	 * create some sets for testing
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		setA = new SetOfNumbers();		
		setB = SetOfNumbers.fromArray( new int[]{ 10, 20, 30, 40, 50, 60});
	
		setC = new SetOfNumbers();
		for (int i = 5; i < 50; i++) {
			setC.add(i * 10);
		}				
                setD = SetOfNumbers.fromArray( new int[]{ 30, 40, 50, 60, 10, 20});
	}

	@AfterEach
	public void tearDown() throws Exception {
		setA = setB = setC = setD = null;
	}

	@Test
	@DisplayName("add valid numbers to the set")
	public void testAdd() {
            
		setA.add( 99 );
		assertTrue( setA.contains(99), "add: added element not found in set." );
		assertEquals(1, setA.size() );
		setA.add( 11 );		
		assertTrue(  setA.contains(11), "add: added element not found in set." );
		assertEquals( 2, setA.size(),  "add: elements count not as expected." );
	}

	@Test
	@DisplayName("two non overlaping sets report no intersection")
	public void testIntersectForNoIntersection() {
		 assertFalse( setA.intersects(setB),  "no intersection was reported as existing"); 
                 
	}
	
	@Test
	@DisplayName("two sets with intersection report it")
	public void testIntersectsForIntersectionNotEmpty() {
		 assertTrue( setB.intersects(setC), "failed to find existing intersection");
		 assertTrue(  setD.intersects(setB), "failed to find existing intersection");
	}

	@Test
	public void testContains() {
		assertTrue(   setB.contains(10), "contains: expected value not found");
		assertTrue(  setB.contains(60), "contains: expected value not found" );
		assertFalse( setB.contains(-1), "contains: non existing value reported found");
		assertFalse(  setB.contains(90), "contains: non existing value reported found" );		
	}

	@Test
	public void testNoDuplicates() {    
	    assertThrows( IllegalArgumentException.class,  ()->setB.add( 20 ));	// duplicate, must fail with an exception	    
	}

	@Test
@DisplayName("test intersection with empty set")
public void testIntersectWithEmptySet() {
    SetOfNumbers setA = new SetOfNumbers();
    SetOfNumbers setB = new SetOfNumbers();

    // Adicionando elementos a setA
    setA.add(10);
    setA.add(20);
    setA.add(30);

    // Não adicionando nada a setB, ele é vazio
    assertFalse(setA.intersects(setB), "intersects: intersection with empty set should be false");
}


	@Test
@DisplayName("subtract elements from the set")
public void testSubtract() {
    SetOfNumbers setA = new SetOfNumbers();
    setA.add(10);
    setA.add(20);
    setA.add(30);

    SetOfNumbers setB = new SetOfNumbers();
    setB.add(20);
    setB.add(30);

    setA.subtract(setB);
    
    // Verificar se os elementos 20 e 30 foram removidos de setA
    assertFalse(setA.contains(20), "subtract: 20 should be removed from setA");
    assertFalse(setA.contains(30), "subtract: 30 should be removed from setA");
    assertTrue(setA.contains(10), "subtract: 10 should remain in setA");
}

@Test
@DisplayName("test hashCode and equals")
public void testHashCodeAndEquals() {
    SetOfNumbers set1 = new SetOfNumbers();
    SetOfNumbers set2 = new SetOfNumbers();
    SetOfNumbers set3 = new SetOfNumbers();
    
    set1.add(10);
    set1.add(20);
    set1.add(30);

    set2.add(10);
    set2.add(20);
    set2.add(30);

    set3.add(40);
    set3.add(50);

	// Testando igualdade com o próprio objeto
	assertTrue(set1.equals(set1), "equals: set should be equal to itself");

	// Testando igualdade com null
	assertFalse(set1.equals(null), "equals: set should not be equal to null");

	// Testando igualdade com outro tipo de objeto
	assertFalse(set1.equals(new Object()), "equals: set should not be equal to an object of another class");

    // Testando igualdade de conjuntos com os mesmos elementos
    assertTrue(set1.equals(set2), "equals: sets with the same elements should be equal");
    
    // Testando desigualdade com conjuntos diferentes
    assertFalse(set1.equals(set3), "equals: sets with different elements should not be equal");
    
    // Testando hashCode para conjuntos iguais
    assertEquals(set1.hashCode(), set2.hashCode(), "hashCode: hash codes of equal sets should be the same");

    // Testando hashCode para conjuntos diferentes
    assertNotEquals(set1.hashCode(), set3.hashCode(), "hashCode: hash codes of different sets should be different");	
	}

	@Test
	@DisplayName("test size of the set")
	public void testSize() {
		SetOfNumbers setA = new SetOfNumbers();
		assertEquals(0, setA.size(), "size: new set should have size 0");

		setA.add(10);
		setA.add(20);
		setA.add(30);
		assertEquals(3, setA.size(), "size: set size should be 3 after adding 3 elements");

		// setA.remove(20);
		// assertEquals(2, setA.size(), "size: set size should be 2 after removing an element");
	}

      
}
