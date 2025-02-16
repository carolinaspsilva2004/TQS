/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 *
 * @author ico0
 */
public class DipTest {
    
	private Dip dip;

    private Dip dip1;
    private Dip dip2;
    private Dip dip3;

    @BeforeEach
    public void setUp() {
        dip = new Dip( new int[] {10,20,30,40,50}, new int[] {1,2});

        dip1 = new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2});
        dip2 = new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2});
        dip3 = new Dip(new int[]{6, 7, 8, 9, 10}, new int[]{3, 4});
    }
    
    @AfterEach
    public void tearDown() {
    	dip = null;
    }

    @Test
    public void testGenerateRandomDip() {
        Dip randomDip1 = Dip.generateRandomDip();
        Dip randomDip2 = Dip.generateRandomDip();

        assertNotNull(randomDip1, "O Dip gerado não deve ser nulo.");
        assertNotNull(randomDip2, "O Dip gerado não deve ser nulo.");
        assertEquals(Dip.REQUIRED_NUMBERS_COUNT_FOR_BET, randomDip1.getNumbersColl().size(), "O Dip deve conter exatamente 5 números.");
        assertEquals(Dip.REQUIRED_STARS_COUNT_FOR_BET, randomDip1.getStarsColl().size(), "O Dip deve conter exatamente 2 estrelas.");
        assertNotEquals(randomDip1, randomDip2, "Dips gerados aleatoriamente devem ser diferentes na maioria das vezes.");
    }
    
   
    @Test
    public void testConstructorFromBadArrays() {
        // Teste com arrays de tamanho errado
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1, 2, 3}, new int[]{1}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1, 2, 3, 4, 5, 6}, new int[]{1, 2}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1}));

        // Teste com números fora dos limites permitidos
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{0, 2, 3, 4, 5}, new int[]{1, 2}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{51, 2, 3, 4, 5}, new int[]{1, 2}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{0, 2}));
        assertThrows(IllegalArgumentException.class, () -> new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{13, 2}));
    }

     
    @Test
    @DisplayName("pretty format of a dip")
    public void testPrettyFormat() {

        // note: correct the implementation, not the test ;)
        String result = dip.format();
        assertEquals( "N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");        
    }

    @Test
    public void testEquals() {
        assertEquals(dip1, dip1, "Dips diferentes não devem ser o mesmo objeto.");
        assertEquals(dip1, dip2, "Dips com os mesmos números e estrelas devem ser iguais.");
        assertNotEquals(dip1, dip3, "Dips com números diferentes não devem ser iguais.");
        assertNotEquals(dip1, null, "Dip não deve ser igual a null.");
        assertNotEquals(dip1, new Object(), "Dip não deve ser igual a um objeto de outra classe.");
    }

    @Test
    public void testHashCode() {
        assertEquals(dip1.hashCode(), dip2.hashCode(), "Objetos iguais devem ter o mesmo hashCode.");
        assertNotEquals(dip1.hashCode(), dip3.hashCode(), "Objetos diferentes devem ter hashCodes diferentes.");
    }


}
