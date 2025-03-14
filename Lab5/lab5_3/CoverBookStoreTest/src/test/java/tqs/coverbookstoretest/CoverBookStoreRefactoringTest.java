package tqs.coverbookstoretest;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(SeleniumJupiter.class)
public class CoverBookStoreRefactoringTest {

    @Test
    void searchForHarryPotterTestWithRefactoring(WebDriver driver) {
        // Ensure WebDriver is set up properly
        WebDriverSingleton.getDriver();

        // Create instance of step definitions and set the driver
        BookstoreSearchSteps bookstoreSearchSteps = new BookstoreSearchSteps();
        bookstoreSearchSteps.setUp(); // Calls @Before method

        // Run test steps
        bookstoreSearchSteps.userIsOnHomePage();
        bookstoreSearchSteps.the_user_searches_for("Harry Potter");
        bookstoreSearchSteps.at_least_one_book_containing_should_be_displayed("Harry Potter");
    }
}
