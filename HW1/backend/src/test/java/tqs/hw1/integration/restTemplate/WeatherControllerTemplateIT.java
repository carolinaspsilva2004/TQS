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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class WeatherControllerTemplateIT {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
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
        String date = "2025-03-28";
        
        // Atualize a condição esperada com base nos dados da resposta
        String expectedCondition = "Partially cloudy";  // Use a condição correta da resposta

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
        String date = "2025-03-28";
        
        // Espera-se que o "current" e "days" estejam presentes na resposta
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
        String date = "2025-03-29";
        
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
        String date = "2025-03-29";
        
        // Espera-se que os "hours" tenham condições específicas na primeira entrada
        String expectedHourCondition = "Partially cloudy";  // Altere para a condição esperada

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/hours")
                .then()
                .statusCode(200)
                .body("[0].conditions", equalTo(expectedHourCondition));  // Nota a mudança aqui!
    }

}
