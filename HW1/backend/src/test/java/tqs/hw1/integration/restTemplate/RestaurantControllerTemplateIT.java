package tqs.hw1.controller.integration.restTemplate;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import tqs.hw1.model.Restaurant;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class RestaurantControllerTemplateIT {

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
    void whenAddRestaurant_thenRestaurantIsCreated() {
        Restaurant restaurant = new Restaurant("Testaurant", "http://menu.example.com");

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(restaurant)
                .post("/restaurants")
                .then()
                .statusCode(200)
                .body("name", equalTo("Testaurant"));    }

    @Test
    void whenGetRestaurants_thenListIsReturned() {
        RestAssured.given()
                .port(port)
                .get("/restaurants")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(0)));
    }
}
