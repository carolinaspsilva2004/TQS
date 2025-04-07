package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import static org.junit.Assert.*;
import java.time.Duration;
import tqs.hw1.seleniumTests.WebDriverSingleton;
import static org.junit.Assert.assertTrue;


public class ReservationSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public ReservationSteps() {
        this.driver = WebDriverSingleton.getDriver();
    }

    @When("the user clicks on a meal to make a reservation")
    public void theUserClicksOnAMealToMakeAReservation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement mealCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".meal-card")));
        WebElement reservarBtn = mealCard.findElement(By.cssSelector(".reservar-btn"));
        reservarBtn.click();
    }

    @Then("a confirmation modal should be displayed")
    public void aConfirmationModalShouldBeDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-container")));
        assertTrue(modal.isDisplayed());
    }

    @When("the user confirms the reservation")
    public void theUserConfirmsTheReservation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-container button.confirm-btn")));
        confirmBtn.click();
    }

    @Then("the success modal should be displayed with a reservation code")
    public void theSuccessModalShouldBeDisplayedWithAReservationCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement successModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success-modal")));
        assertTrue(successModal.isDisplayed());

        WebElement message = successModal.findElement(By.cssSelector("p, .modal-message")); // Ajusta conforme o HTML do modal
        assertTrue(message.getText().contains("Código de reserva"));
    }
}
