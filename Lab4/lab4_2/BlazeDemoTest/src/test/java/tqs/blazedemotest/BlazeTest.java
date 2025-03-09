package tqs.blazedemotest;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumJupiter.class)
public class BlazeTest {

    @Test
    public void comprarVooTest(WebDriver driver) {
        // 1. Acessar o site
        driver.get("https://blazedemo.com/");

        // 2. Definir o tamanho da janela
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(550, 691));

        // 3. Clicar no botão "Find Flights"
        WebElement findFlightsButton = driver.findElement(By.cssSelector(".btn-primary"));
        findFlightsButton.click();

        // 4. Verificar se o preço do voo é 234
        WebElement priceElement = driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(3)"));
        assertEquals("234", priceElement.getText(), "The flight price should be 234.");

        // 5. Verificar se o título do voo corresponde à origem e destino
        WebElement flightTitleElement = driver.findElement(By.cssSelector("h3"));
        assertTrue(flightTitleElement.getText().contains("Flights from Paris to Buenos Aires"));

        // 6. Escolher o segundo voo da lista
        WebElement chooseFlightButton = driver.findElement(By.cssSelector("tr:nth-child(2) .btn"));
        chooseFlightButton.click();

        // 7. Preencher os dados pessoais
        WebElement inputName = driver.findElement(By.id("inputName"));
        inputName.sendKeys("Taylor Swift");

        WebElement address = driver.findElement(By.id("address"));
        address.sendKeys("Nashville, EUA");

        WebElement city = driver.findElement(By.id("city"));
        city.sendKeys("Nashville");

        WebElement state = driver.findElement(By.id("state"));
        state.sendKeys("Tennessee");

        WebElement zipCode = driver.findElement(By.id("zipCode"));
        zipCode.sendKeys("5839-021");

        WebElement creditCardNumber = driver.findElement(By.id("creditCardNumber"));
        creditCardNumber.sendKeys("94004957290485");

        WebElement nameOnCard = driver.findElement(By.id("nameOnCard"));
        nameOnCard.sendKeys("Taylor Swift");

        WebElement rememberMeCheckbox = driver.findElement(By.id("rememberMe"));
        rememberMeCheckbox.click();

        // 8. Confirmar a compra do voo
        WebElement purchaseFlightButton = driver.findElement(By.cssSelector(".btn-primary"));
        purchaseFlightButton.click();

        // 9. Verificar a confirmação da compra
        WebElement confirmationMessage = driver.findElement(By.cssSelector("h1"));
        assertEquals("Thank you for your purchase today!", confirmationMessage.getText(), "Confirmation message should be displayed.");

        // 10. Verificar o título da página de confirmação
        String pageTitle = driver.getTitle();
        assertEquals("BlazeDemo Confirmation", pageTitle, "The page title should be 'BlazeDemo Confirmation'");
    }
}
