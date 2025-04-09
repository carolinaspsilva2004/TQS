package tqs.hw1.controller.integration.restTemplate;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
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
        System.out.println("🚀 PostgreSQL container iniciado.");
    }

    @Test
    void whenGetWeatherForCityAndDate_thenReturnWeather() {
        String city = "Aveiro";
        String date = "2025-04-10";
        String expectedCondition = "Partially cloudy";  

        System.out.println("🌤️ Teste: previsão diária para " + city + " em " + date);

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date)
                .then()
                .statusCode(200)
                .body("days[0].conditions", equalTo(expectedCondition));

        System.out.println("✅ Condição esperada encontrada: " + expectedCondition);
    }

    @Test
    void whenGetWeatherDaysAndCurrent_thenReturnCorrectData() {
        String city = "Aveiro";
        String date = "2025-04-10";
        String expectedCurrentCondition = "Partially cloudy"; 

        System.out.println("🌥️ Teste: condição atual para " + city + " em " + date);

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/current")
                .then()
                .statusCode(200)
                .body("currentConditions.conditions", equalTo(expectedCurrentCondition));

        System.out.println("✅ Condição atual esperada encontrada: " + expectedCurrentCondition);
    }

    @Test
    void whenGetWeatherAlerts_thenReturnAlerts() {
        String city = "Aveiro";
        String date = "2025-04-07";

        System.out.println("⚠️ Teste: alertas meteorológicos para " + city + " em " + date);

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/alerts")
                .then()
                .statusCode(200)
                .body("alerts", empty());

        System.out.println("✅ Sem alertas retornados (lista vazia)");
    }

    @Test
    void whenGetWeatherHours_thenReturnHourlyData() {
        String city = "Aveiro";
        String date = "2025-04-11";
        String expectedHourCondition = "Rain, Overcast"; 

        System.out.println("🕓 Teste: dados horários para " + city + " em " + date);

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date + "/hours")
                .then()
                .statusCode(200)
                .body("[0].conditions", equalTo(expectedHourCondition));  

        System.out.println("✅ Condição horária esperada encontrada: " + expectedHourCondition);
    }

    @Test
    @DisplayName("Cache deve armazenar a previsão para evitar requisições duplicadas")
    void whenRequestSameDataTwice_thenCacheShouldBeUsed() {
        String city = "Aveiro";
        String date = "2025-04-08";

        System.out.println("📦 Teste de cache: primeira chamada para " + city + " em " + date);

        RestAssured.given()
                .port(port)
                .get("/weather/" + city + "/" + date)
                .then()
                .statusCode(200);

        System.out.println("📦 Segunda chamada: vamos verificar estatísticas do cache");

        RestAssured.given()
                .port(port)
                .get("/weather/cache/stats")
                .then()
                .statusCode(200)
                .body(containsString("Total Requests: 3, Cache Hits: 0, Cache Misses: 3"));

        System.out.println("✅ Estatísticas de cache verificadas com sucesso");
    }
}
