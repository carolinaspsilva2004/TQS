package tqs.webdriver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

import java.time.Duration;

@ExtendWith(SeleniumJupiter.class) 
public class HelloWorldFirefoxSelJupTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void test(WebDriver driver) {
        
        String sutUrl = "https://www.selenium.dev/selenium/web/web-form.html";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        assertThat(title).isEqualTo("Web form");

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        String value = message.getText();

        assertThat(value).isEqualTo("Received!");

        log.debug("Test completed successfully");
    }

}