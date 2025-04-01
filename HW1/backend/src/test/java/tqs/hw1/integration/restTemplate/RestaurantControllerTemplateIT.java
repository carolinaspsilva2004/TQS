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
        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Testaurant\"}")
                .post("/restaurants")
                .then()
                .statusCode(200)
                .body("name", equalTo("Testaurant"));
    }

    @Test
    void whenGetRestaurants_thenListIsReturned() {
        RestAssured.given()
                .port(port)
                .get("/restaurants")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(0)));
    }

    @Test
    void whenGetRestaurantById_thenCorrectRestaurantIsReturned() {
        String restaurantId = RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Testaurant\"}")
                .post("/restaurants")
                .then()
                .extract().path("id").toString();

        RestAssured.given()
                .port(port)
                .get("/restaurants/" + restaurantId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Testaurant"));
    }

    @Test
    void whenDeleteRestaurant_thenItIsRemoved() {
        String restaurantId = RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"ToBeDeleted\"}")
                .post("/restaurants")
                .then()
                .extract().path("id").toString();

        RestAssured.given()
                .port(port)
                .delete("/restaurants/" + restaurantId)
                .then()
                .statusCode(200)
                .body(equalTo("Restaurante removido com sucesso."));
    }
}
