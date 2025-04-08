package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import tqs.hw1.seleniumTests.WebDriverSingleton;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions; // Corrigido para usar Assertions do JUnit 5

public class DetailsCancelSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    private int currentReservationCount;

    private static final Logger logger = Logger.getLogger(DetailsCancelSteps.class.getName());

    public DetailsCancelSteps() {
        this.driver = WebDriverSingleton.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("the user navigates to the {string} page")
    public void theUserNavigatesToThePage(String pageName) {
        driver.get("http://localhost:3000/reservas"); // Ou altera conforme necessário
        logger.info("Navigated to the 'My Reservations' page");
    }

    @When("the user sees the list of active reservations")
    public void theUserSeesTheListOfActiveReservations() {
        List<WebElement> reservations = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.cssSelector(".reservations-list .reservation-card")
        ));
        currentReservationCount = reservations.size(); // salva para verificação posterior
        Assertions.assertFalse(reservations.isEmpty(), "No active reservations found.");
        logger.info("Active reservations found: " + currentReservationCount);
    }

    @When("the user chooses to cancel a reservation")
    public void theUserChoosesToCancelAReservation() {
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".reservation-card .cancel-btn")
        ));
        cancelButton.click();
        logger.info("Cancel button clicked.");
    }

    @When("the user confirms the cancellation")
    public void theUserConfirmsTheCancellation() {
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".modal-buttons .confirm-btn")
        ));
        confirmButton.click();
        logger.info("Cancellation confirmed.");
    }

    @Then("the reservation should be removed from the list")
    public void theReservationShouldBeRemovedFromTheList() {
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
            By.cssSelector(".reservation-card"),
            currentReservationCount
        ));
        logger.info("Reservation successfully removed from the list.");
    }

    @Then("a success message should be displayed")
    public void aSuccessMessageShouldBeDisplayed() {
        WebElement successModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".success-modal-overlay")
        ));
        Assertions.assertTrue(successModal.isDisplayed(), "Success modal was not displayed.");
        logger.info("Success message displayed.");
    }
}
