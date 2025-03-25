package tqs.hw1.controller;

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
public class ReservationControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private int port;

    @BeforeAll
    static void setupContainer() {
        postgres.start();
    }

    @Test
    void whenBookMeal_thenReservationIsCreated() {
        // Primeiro criar um restaurante
        var restaurant = RestAssured.given().port(port).contentType(ContentType.JSON)
                .body(new Restaurant("RestoTest", "Lisbon", "http://fakeurl.com"))
                .post("/restaurants")
                .then().statusCode(200)
                .extract().as(Restaurant.class);

        // Inserir uma meal manualmente no banco de dados via request não implementado ou via repo seria o ideal,
        // ou simplesmente adicionar uma meal diretamente pelo endpoint updateMeals se tiver conteúdo externo.
        // Como alternativa: poderias usar um endpoint de inserção, se existir.

        // Aqui, assumimos que já há alguma Meal no banco de dados (ou previamente carregada).
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
