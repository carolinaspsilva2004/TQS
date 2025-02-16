package ui;

import euromillions.CuponEuromillions;
import euromillions.Dip;
import euromillions.EuromillionsDraw;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class DemoMainTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));  // Captura saída do console
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);  // Restaura saída original
    }

    @Test
    @DisplayName("Test main method output")
    public void testMainMethod() {
        // Executa o main
        DemoMain.main(new String[]{});

        // Captura a saída e transforma em string
        String output = outputStream.toString();

        // Verifica se contém os principais elementos esperados
        assertTrue(output.contains("Betting with three random bets..."), "Main output should contain betting message.");
        assertTrue(output.contains("You played:"), "Main output should contain 'You played' section.");
        assertTrue(output.contains("Draw results:"), "Main output should contain 'Draw results' section.");
        assertTrue(output.contains("Your score:"), "Main output should contain 'Your score' section.");
    }
}
