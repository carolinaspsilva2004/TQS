package tqs.pageobject;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class CoverBookStorePageObjectTest {

    private CoverBookStorePage coverBookStorePage;

    @BeforeEach
    void setUp(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://cover-bookstore.onrender.com/");
        coverBookStorePage = PageFactory.initElements(driver, CoverBookStorePage.class);
    }

    @Test
    void searchForHarryPotterTest() {
        coverBookStorePage.searchForBook("Harry Potter");
        assertThat(coverBookStorePage.getFirstSearchResult()).contains("Harry Potter and the Sorcerer's Stone");
    }
}
