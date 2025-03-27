package tqs.hw1.controller.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;

import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ReservationControllerTemplateIT {

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
    void whenBookMeal_thenReservationIsCreated() {
        var restaurant = RestAssured.given().port(port).contentType(ContentType.JSON)
                .body(new Restaurant("RestoTest", "Lisbon", "http://fakeurl.com"))
                .post("/restaurants")
                .then().statusCode(200)
                .extract().as(Restaurant.class);

        
        var meals = RestAssured.given().port(port)
                .get("/meals")
                .then().statusCode(200)
                .extract().jsonPath().getList(".", Meal.class);

        if (!meals.isEmpty()) {
            Meal meal = meals.get(0);

            RestAssured.given()
                    .port(port)
                    .post("/reservations/book/" + meal.getId())
                    .then()
                    .statusCode(200)
                    .body("used", equalTo(false))
                    .body("meal.id", equalTo(meal.getId().intValue()));
        }
    }
}
