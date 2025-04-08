package tqs.hw1.seleniumTests;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.logging.Logger;


public class Hooks {

    private static final Logger logger = Logger.getLogger(Hooks.class.getName());

    @Before
    public void setUp() {
        logger.info("Initializing WebDriver...");
        WebDriverSingleton.getDriver();  // Cria se não existir
        logger.info("WebDriver initialized successfully.");
    }

    @After
    public void tearDown() {
        logger.info("Tearing down WebDriver...");
        WebDriverSingleton.quitDriver();  // Encerra e zera
        logger.info("WebDriver quit successfully.");
    }
}
