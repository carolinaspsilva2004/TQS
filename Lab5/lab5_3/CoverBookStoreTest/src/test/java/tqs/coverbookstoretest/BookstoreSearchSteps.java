package tqs.coverbookstoretest;

import io.cucumber.java.en.*;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BookstoreSearchSteps {

    private final WebDriver driver;
    private WebDriverWait wait;

    public BookstoreSearchSteps(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("the user is on the bookstore homepage")
    public void userIsOnHomePage() {
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("the user searches for {string}")
    public void userSearchesFor(String searchQuery) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Search for books, authors, etc...']")
        ));
        searchBox.sendKeys(searchQuery);
        searchBox.sendKeys(Keys.RETURN);
    }

    @Then("at least one book containing {string} should be displayed")
    public void atLeastOneBookShouldBeDisplayed(String expectedTitle) {
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid=book-search-item]")
        ));
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getText()).contains(expectedTitle);
    }

    @Then("at least one book by {string} should be displayed")
    public void atLeastOneBookByAuthorShouldBeDisplayed(String expectedAuthor) {
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid=book-search-item]")
        ));
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getText()).contains(expectedAuthor);
    }

    @Then("no search results should be displayed")
    public void noResultsShouldBeDisplayed() {
        assertThat(driver.findElements(By.cssSelector("[data-testid=book-search-item]"))).isEmpty();
    }

    @When("the user performs an empty search")
    public void userPerformsEmptySearch() {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Search for books, authors, etc...']")
        ));
        searchBox.sendKeys(Keys.RETURN);
    }

    @Then("all books should be displayed")
    public void allBooksShouldBeDisplayed() {
        assertThat(driver.findElements(By.cssSelector("[data-testid=book-search-item]")).size()).isGreaterThan(1);
    }
}
