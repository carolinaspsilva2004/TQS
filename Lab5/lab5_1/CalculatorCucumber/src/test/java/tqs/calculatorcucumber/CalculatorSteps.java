package tqs.calculatorcucumber;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.slf4j.Logger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {
    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;
    private Exception exception; // Declaração da variável exception

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I subtract {int} from {int}")
    public void subtract(int arg1, int arg2) {
        log.debug("Subtracting {} from {}", arg1, arg2);
        calc.push(arg2);
        calc.push(arg1);
        calc.push("-");
    }

    @When("I multiply {int} by {int}")
    public void multiply(int arg1, int arg2) {
        log.debug("Multiplying {} by {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("*");
    }

    @When("I divide {int} by {int}")
    public void divide(int arg1, int arg2) {
        log.debug("Dividing {} by {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("/");
    }

    @When("I perform an invalid operation")
    public void invalidOperation() {
        log.debug("Performing invalid operation");
        try {
            calc.push("%"); // Operação inválida
        } catch (IllegalArgumentException e) {
            log.error("Caught expected exception: {}", e.getMessage());
            this.exception = e; // Guarda a exceção para verificação no Then
        }
    }

    @Then("an error is thrown")
    public void errorIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            if (this.exception != null) {
                throw this.exception;
            }
        });
    }

    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }
}
