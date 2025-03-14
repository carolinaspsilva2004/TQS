package tqs.coverbookstoretest;

import io.cucumber.java.en.*;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.Before;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;

@ExtendWith(SeleniumJupiter.class)
public class BookstoreSearchSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public BookstoreSearchSteps() {
    }

    // WebDriver will be provided before any scenario runs
    @Before
    public void setUp() {
        this.driver = WebDriverSingleton.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Given("the user is on the bookstore homepage")
    public void userIsOnHomePage() {
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String title) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Search for books, authors, etc...']")
        ));
        searchBox.sendKeys(title);
        searchBox.sendKeys(Keys.RETURN);
    }

    @Then("no search results should be displayed")
    public void no_search_results_should_be_displayed() {
        int bookCount = driver.findElements(By.cssSelector("[data-testid=book-search-item]")).size();
        assertThat(bookCount).isEqualTo(0);
    }

    @Then("at least one book containing {string} should be displayed")
    public void at_least_one_book_containing_should_be_displayed(String title) {
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid=book-search-item]")
        ));
        assertThat(searchResult.getText()).contains(title);
    }

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".search-error-message") // Ajuste este seletor conforme necessário
        ));
        assertThat(errorMsg.getText()).contains("Please enter a search term");
    }

    @When("the user searches for books by {string}")
    public void the_user_searches_for_books_by(String author) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Search for books, authors, etc...']")
        ));
        searchBox.sendKeys(author);
        searchBox.sendKeys(Keys.RETURN);
    }

    @Then("at least one book by {string} should be displayed")
    public void at_least_one_book_by_should_be_displayed(String author) {
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid=book-search-item]")
        ));
        assertThat(searchResult.getText()).contains(author);
    }



}
