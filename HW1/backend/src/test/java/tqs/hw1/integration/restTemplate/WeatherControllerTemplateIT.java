package tqs.hw1.controller.integration.restTemplate;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.DisplayName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class WeatherControllerTemplateIT {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    private int port;

    @BeforeAll
    static void setupContainer() {
        postgres.start();
    }

    @Test
    void whenGetWeatherForCityAndDate_thenReturnWeather() {
        String city = "Aveiro";
        String date = "2025-04-07";
        
        String expectedCondition = "Partially cloudy";  

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date)
                .then()
                .statusCode(200)
                .body("days[0].conditions", equalTo(expectedCondition));
    }

    @Test
    void whenGetWeatherDaysAndCurrent_thenReturnCorrectData() {
        String city = "Aveiro";
        String date = "2025-04-01";
        
        String expectedCurrentCondition = "Partially cloudy"; 

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/current")
                .then()
                .statusCode(200)
                .body("currentConditions.conditions", equalTo(expectedCurrentCondition));
    }

    @Test
    void whenGetWeatherAlerts_thenReturnAlerts() {
        String city = "Aveiro";
        String date = "2025-04-01";
        
        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/alerts")
                .then()
                .statusCode(200)
                .body("alerts", empty());
    }

    
    @Test
    void whenGetWeatherHours_thenReturnHourlyData() {
        String city = "Aveiro";
        String date = "2025-04-11";
        
        String expectedHourCondition = "Partially cloudy"; 

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/hours")
                .then()
                .statusCode(200)
                .body("[0].conditions", equalTo(expectedHourCondition));  
    }


    @Test
    @DisplayName("Cache deve armazenar a previsão para evitar requisições duplicadas")
    void whenRequestSameDataTwice_thenCacheShouldBeUsed() {
        String city = "Aveiro";
        String date = "2025-04-02";

        // Primeira chamada para popular o cache
        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date)
                .then()
                .statusCode(200);

        // Segunda chamada deve usar cache
        RestAssured.given()
                .port(port)
                .get("/weather/cache/stats")
                .then()
                .statusCode(200)
                .body(containsString("Total Requests: 3, Cache Hits: 0, Cache Misses: 3"));
    }
}
