package tqs.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;

public class CoverBookStorePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "input[placeholder='Search for books, authors, etc...']")
    private WebElement searchBox;

    @FindBy(css = "[data-testid=book-search-item]")
    private WebElement firstSearchResult;

    public CoverBookStorePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Tempo maior
        PageFactory.initElements(driver, this); // 🔥 ESSA LINHA É FUNDAMENTAL!
    }

    public void searchForBook(String bookName) {
        wait.until(ExpectedConditions.visibilityOf(searchBox)).sendKeys(bookName);
        searchBox.sendKeys(Keys.RETURN);
    }

    public String getFirstSearchResult() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid=book-search-item]"))).getText();
    }
}
