package tqs.pageobject;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class CoverBookStorePageObjectTest {

    private CoverBookStorePage coverBookStorePage;
    private WebDriver driver;

    @BeforeEach
    void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.LINUX);
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://cover-bookstore.onrender.com/");
        coverBookStorePage = new CoverBookStorePage(driver);
    }

    @Test
    void searchForHarryPotterTest() {
        coverBookStorePage.searchForBook("Harry Potter");
        assertThat(coverBookStorePage.getFirstSearchResult()).contains("Harry Potter and the Sorcerer's Stone");
    }

     @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); // 🔥 Fecha o navegador corretamente!
        }
    }
}
