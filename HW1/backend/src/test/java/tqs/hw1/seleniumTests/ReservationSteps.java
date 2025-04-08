package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import tqs.hw1.seleniumTests.WebDriverSingleton;
import java.time.Duration;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;

public class ReservationSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(ReservationSteps.class.getName());

    public ReservationSteps() {
        this.driver = WebDriverSingleton.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("the user clicks on a meal to make a reservation")
    public void theUserClicksOnAMealToMakeAReservation() {
        logger.info("Attempting to click on a meal to start reservation...");
        try {
            WebElement mealCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".meal-card")));
            WebElement reservarBtn = mealCard.findElement(By.cssSelector(".reservar-btn"));
            reservarBtn.click();
            logger.info("Successfully clicked the reservation button.");
        } catch (Exception e) {
            logger.severe("Failed to click on a meal for reservation: " + e.getMessage());
            throw e;
        }
    }

    @Then("a confirmation modal should be displayed")
    public void aConfirmationModalShouldBeDisplayed() {
        logger.info("Verifying if the confirmation modal is displayed...");
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".modal-content")
            ));
            assertTrue("Confirmation modal should be visible", modal.isDisplayed());
            logger.info("Confirmation modal is visible.");
        } catch (Exception e) {
            logger.severe("Failed to display confirmation modal: " + e.getMessage());
            throw e;
        }
    }

    @When("the user confirms the reservation")
    public void theUserConfirmsTheReservation() {
        logger.info("Attempting to confirm the reservation...");
        try {
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.modal-btn.confirm")));
            confirmBtn.click();
            logger.info("Reservation confirmed successfully.");
        } catch (Exception e) {
            logger.severe("Failed to confirm reservation: " + e.getMessage());
            throw e;
        }
    }

    @Then("the success modal should be displayed with a reservation code")
    public void theSuccessModalShouldBeDisplayedWithAReservationCode() {
        logger.info("Checking if success modal is displayed with reservation code...");
        try {
            WebElement successModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success-modal-content")));
            assertTrue("Success modal should be visible", successModal.isDisplayed());
            logger.info("Success modal is visible.");

            WebElement message = successModal.findElement(By.cssSelector("h2, .success-modal-content")); // Ajuste conforme necessário
            String modalText = message.getText();
            logger.info("Success modal message: " + modalText);

            assertTrue("Modal message should contain reservation code", modalText.contains("Código de reserva"));
            logger.info("Reservation code found in success modal.");
        } catch (Exception e) {
            logger.severe("Failed to verify success modal or reservation code: " + e.getMessage());
            throw e;
        }
    }
}
