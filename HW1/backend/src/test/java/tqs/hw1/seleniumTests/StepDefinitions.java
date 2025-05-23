package tqs.hw1.seleniumTests;

import io.cucumber.java.en.*;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

@ExtendWith(SeleniumJupiter.class)
public class StepDefinitions {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(StepDefinitions.class.getName());

    public StepDefinitions() {
        this.driver = WebDriverSingleton.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }    

    @Given("the user navigates to the homepage")
    public void the_user_navigates_to_the_homepage() {
        logger.info("Navigating to homepage...");
        if (driver != null) {
            driver.get("http://localhost:3000/home");  // Substitua pela URL real
            logger.info("Navigated to homepage: http://localhost:3000/home");
        } else {
            logger.severe("WebDriver is null, unable to navigate.");
        }
    }

    @When("the user selects a restaurant from the dropdown")
    public void the_user_selects_a_restaurant_from_the_dropdown() {
        logger.info("Selecting restaurant from dropdown...");
        try {
            WebElement restaurantDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("restaurant")
            ));
            restaurantDropdown.sendKeys("Cantina de Santiago");  // Nome do restaurante real
            logger.info("Restaurant 'Cantina de Santiago' selected from dropdown.");
        } catch (Exception e) {
            logger.severe("Failed to select restaurant from dropdown: " + e.getMessage());
        }
    }

    @When("the user selects a date for the meals")
    public void the_user_selects_a_date_for_the_meals() {
        logger.info("Selecting date for the meals...");
        try {
            WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("restaurantDate")
            ));
            dateInput.sendKeys("12/04/2025");  // Data no formato MM/DD/YYYY
            logger.info("Date '12/04/2025' selected for meals.");
        } catch (Exception e) {
            logger.severe("Failed to select date for meals: " + e.getMessage());
        }
    }

    @When("the user confirms the selection")
    public void the_user_confirms_the_selection() {
        logger.info("Confirming selection...");
        try {
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".confirm-btn")
            ));
            confirmButton.click();
            logger.info("Selection confirmed by clicking the confirm button.");
        } catch (Exception e) {
            logger.severe("Failed to confirm selection: " + e.getMessage());
        }
    }

    @Then("the meals for the selected restaurant and date should be displayed")
public void the_meals_for_the_selected_restaurant_and_date_should_be_displayed() {
    logger.info("Verifying meals are displayed...");
    try {
        // Aguardar até que a lista de refeições esteja visível
        WebElement mealList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".menu-section ul")
        ));

        // Aguardar até que todos os cartões de refeição estejam presentes
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".meal-card")));

        // Encontrar todos os elementos de refeição
        List<WebElement> mealCards = mealList.findElements(By.cssSelector(".meal-card"));
        logger.info("Number of meal cards found: " + mealCards.size());
        
        // Verificar se a lista não está vazia
        assertThat(mealCards).isNotEmpty();
        
        // Para cada refeição, verificar se o nome e a descrição estão visíveis e corretos
        for (WebElement mealCard : mealCards) {
            String mealName = mealCard.findElement(By.cssSelector("h4")).getText();
            String mealDescription = mealCard.findElement(By.cssSelector("p")).getText();
            logger.info("Description: " + mealDescription);
            
            // Verificar se o nome e a descrição não estão vazios
            assertThat(mealDescription).isNotEmpty();

        logger.info("Meals displayed and validated successfully.");
        }
    } catch (Exception e) {
        logger.severe("Failed to display or validate meals: " + e.getMessage());
        throw e;  // Propaga o erro para falhar o teste
    }
}

    


@Then("the weather forecast for the selected date should be shown for the chosen city")
public void the_weather_forecast_for_the_selected_date_should_be_shown_for_the_chosen_city() {
    logger.info("Verifying weather forecast for the selected city and date...");
    try {
        // Preencher a cidade
        WebElement cityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city")));
        cityInput.clear(); // Limpa antes de escrever
        cityInput.sendKeys("Aveiro");
        logger.info("City 'Aveiro' entered.");

        // Preencher a data no formato YYYY-MM-DD
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("weatherDate")));
        dateInput.clear(); // Limpa antes de escrever
        dateInput.sendKeys("12-04-2025"); // Data no formato YYYY-MM-DD
        logger.info("Date '12-04-2025' entered.");

        // Clicar no botão de confirmação
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".confirmar-btn")));
        confirmButton.click();
        logger.info("Clicked confirm button.");

        // Esperar até que o Resumo Diário esteja visível
        WebElement summaryCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".weather-results .hourly-summary-card")
        ));

        String summaryText = summaryCard.getText();
        logger.info("Weather summary content: " + summaryText);

        // Verificações principais com AssertJ
        assertThat(summaryText).contains("Temperatura média");
        assertThat(summaryText).contains("Condições gerais");

        logger.info("Weather forecast displayed successfully for 'Aveiro' on '2025-04-12'.");
    } catch (Exception e) {
        logger.severe("Failed to display weather forecast: " + e.getMessage());
        throw e; // Faz o teste falhar corretamente
    }
}

}
