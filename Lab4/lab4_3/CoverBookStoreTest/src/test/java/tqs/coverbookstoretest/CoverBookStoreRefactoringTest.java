package tqs.coverbookstoretest;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class CoverBookStoreRefactoringTest {

    @Test
    void searchForHarryPotterTestWithRefactoring(WebDriver driver) {
        // 1. Access the bookstore website
        driver.get("https://cover-bookstore.onrender.com/");

        // 2. Espera explícita para garantir que a caixa de pesquisa seja carregada
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Search for books, authors, etc...']")
        ));

        // 3. Enviar a busca com a tecla Enter
        searchBox.sendKeys("Harry Potter");
        searchBox.sendKeys(Keys.RETURN);  // Simula o pressionamento da tecla Enter

        // 4. Espera explícita para aguardar os resultados da busca
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid=book-search-item]") 
        ));

        // 5. Verificar se o resultado contém o livro "Harry Potter"
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getText()).contains("Harry Potter and the Sorcerer's Stone");
    }
}
