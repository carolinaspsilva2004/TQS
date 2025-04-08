package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import tqs.hw1.seleniumTests.WebDriverSingleton;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;

public class AdminVerificationSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(AdminVerificationSteps.class.getName());

    public AdminVerificationSteps() {
        this.driver = WebDriverSingleton.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("the admin navigates to the {string} page")
    public void theAdminNavigatesToThePage(String pageName) {
        driver.get("http://localhost:3000/admin");
        logger.info("Navigated to the Admin Page.");
    }

    @Given("there is at least one reservation with status {string}")
    public void thereIsAtLeastOneReservationWithStatus(String status) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".reservations-list .reservation-item")
        ));

        List<WebElement> reservations = driver.findElements(By.cssSelector(".reservation-item.pending"));
        Assertions.assertFalse(reservations.isEmpty(), "No pending reservations found.");
        logger.info("Pending reservations found: " + reservations.size());
    }

    @When("the admin clicks to mark the reservation as used")
    public void theAdminClicksToMarkTheReservationAsUsed() {
        WebElement verifyButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".reservation-item.pending .verify-btn")
        ));
        verifyButton.click();
        logger.info("Clicked to mark reservation as used.");
    }

    @Then("the reservation status should change to {string}")
    public void theReservationStatusShouldChangeTo(String expectedStatus) {
        // Aguarda que o item seja atualizado com a classe 'used'
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".reservation-item.used")
        ));

        WebElement usedStatus = driver.findElement(
            By.cssSelector(".reservation-item.used .reservation-details p:nth-child(4)")
        );
        Assertions.assertTrue(usedStatus.getText().contains(expectedStatus),
            "Reservation status did not update to: " + expectedStatus);
        logger.info("Reservation marked as used.");
    }

    @Then("the verify button should no longer be visible")
    public void theVerifyButtonShouldNoLongerBeVisible() {
        List<WebElement> verifyButtons = driver.findElements(By.cssSelector(".reservation-item.used .verify-btn"));
        Assertions.assertTrue(verifyButtons.isEmpty(), "Verify button is still visible after check-in.");
        logger.info("Verify button hidden after marking as used.");
    }
}
