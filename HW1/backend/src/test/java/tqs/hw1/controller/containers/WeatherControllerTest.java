package tqs.hw1.controller.containers;

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

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class WeatherControllerTest {

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
        String date = "2025-03-25";
        
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
        String date = "2025-03-25";
        
        // Espera-se que o "current" e "days" estejam presentes na resposta
        String expectedCurrentCondition = "Partially cloudy";  // Substitua pelo valor esperado do "current"

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/current")
                .then()
                .statusCode(200)
                .body("current.conditions", equalTo(expectedCurrentCondition));
    }

    @Test
    void whenGetWeatherAlerts_thenReturnAlerts() {
        String city = "Aveiro";
        String date = "2025-03-25";
        
        // Espera-se que o "alerts" esteja presente na resposta
        String expectedAlert = "Flood warning";  // Substitua pelo valor esperado de alertas

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/alerts")
                .then()
                .statusCode(200)
                .body("alerts[0].description", equalTo(expectedAlert));
    }

    @Test
    void whenGetWeatherEvents_thenReturnEvents() {
        String city = "Aveiro";
        String date = "2025-03-25";
        
        // Espera-se que os "events" estejam presentes na resposta
        String expectedEvent = "Thunderstorm";  // Substitua pelo valor esperado de eventos

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/events")
                .then()
                .statusCode(200)
                .body("events[0].event", equalTo(expectedEvent));
    }

    @Test
    void whenGetWeatherHours_thenReturnHourlyData() {
        String city = "Aveiro";
        String date = "2025-03-25";
        
        // Espera-se que os "hours" estejam presentes na resposta
        String expectedHourCondition = "Clear";  // Substitua pelo valor esperado das condições horárias

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/hours")
                .then()
                .statusCode(200)
                .body("hours[0].conditions", equalTo(expectedHourCondition));
    }
}
