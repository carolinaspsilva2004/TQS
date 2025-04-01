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
import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ReservationControllerTemplateIT {

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
    void whenBookMeal_thenReservationIsCreated() {
        // Criar um restaurante
        Restaurant restaurant = new Restaurant("RestoTest");

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(restaurant)
                .post("/restaurants")
                .then()
                .statusCode(201);

        // Criar uma refeição associada ao restaurante
        LocalDate date = LocalDate.parse("2025-03-25");
        Meal meal = new Meal("MealTest", date, restaurant);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(meal)
                .post("/meals")
                .then()
                .statusCode(201);

        // Criar uma reserva utilizando o construtor com todos os parâmetros necessários
        LocalDateTime reservationDate = LocalDate.now().atStartOfDay();
        Reservation reservation = new Reservation("someCode", reservationDate, false, meal);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .post("/reservations/book/" + meal.getId())
                .then()
                .statusCode(201)
                .body("used", is(false))
                .body("meal.id", is(meal.getId().intValue()));
    }

    @Test
    void whenMealNotFound_thenReturnNotFound() {
        Long nonExistentMealId = 999L;

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .post("/reservations/book/" + nonExistentMealId)
                .then()
                .statusCode(404)
                .body("message", containsString("Meal not found"));
    }

    @Test
    void whenCheckReservation_thenReturnReservation() {
        // Criar um restaurante
        Restaurant restaurant = new Restaurant("RestoTest");

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(restaurant)
                .post("/restaurants")
                .then()
                .statusCode(201);

        // Criar uma refeição associada ao restaurante
        LocalDate date = LocalDate.parse("2025-03-25");
        Meal meal = new Meal("MealTest", date, restaurant);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(meal)
                .post("/meals")
                .then()
                .statusCode(201);

        // Criar uma reserva
        LocalDateTime reservationDate = LocalDate.now().atStartOfDay();
        Reservation reservation = new Reservation("someCode", reservationDate, false, meal);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservation)
                .post("/reservations/book/" + meal.getId())
                .then()
                .statusCode(201);

        // Verificar se a reserva pode ser recuperada por código
        RestAssured.given()
                .port(port)
                .get("/reservations/" + reservation.getCode())
                .then()
                .statusCode(200)
                .body("code", is(reservation.getCode()))
                .body("meal.id", is(meal.getId().intValue()));
    }

    @Test
    void whenCheckIn_thenReturnSuccess() {
        // Criar um restaurante
        Restaurant restaurant = new Restaurant("RestoTest");

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(restaurant)
                .post("/restaurants")
                .then()
                .statusCode(201);

        // Criar uma refeição associada ao restaurante
        LocalDate date = LocalDate.parse("2025-03-25");
        Meal meal = new Meal("MealTest", date, restaurant);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(meal)
                .post("/meals")
                .then()
                .statusCode(201);

        // Criar a reserva
        LocalDateTime reservationDate = LocalDate.now().atStartOfDay();
        Reservation reservation = new Reservation("someCode", reservationDate, false, meal);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservation)
                .post("/reservations/book/" + meal.getId())
                .then()
                .statusCode(201);

        // Realizar check-in
        RestAssured.given()
                .port(port)
                .post("/reservations/checkin/" + reservation.getCode())
                .then()
                .statusCode(200)
                .body("message", equalTo("Check-in successful"));
    }

    @Test
    void whenCheckInReservationNotFound_thenReturnNotFound() {
        String invalidCode = "INVALID_CODE";

        RestAssured.given()
                .port(port)
                .post("/reservations/checkin/" + invalidCode)
                .then()
                .statusCode(404)
                .body("message", equalTo("Reservation not found or already used"));
    }

    @Test
    void whenGetAllReservations_thenListIsReturned() {
        // Criar um restaurante
        Restaurant restaurant = new Restaurant("RestoTest");

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(restaurant)
                .post("/restaurants")
                .then()
                .statusCode(201);

        // Criar uma refeição associada ao restaurante
        LocalDate date = LocalDate.parse("2025-03-25");
        Meal meal = new Meal("MealTest", date, restaurant);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(meal)
                .post("/meals")
                .then()
                .statusCode(201);

        // Criar a reserva
        LocalDateTime reservationDate = LocalDate.now().atStartOfDay();
        Reservation reservation = new Reservation("someCode", reservationDate, false, meal);

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservation)
                .post("/reservations/book/" + meal.getId())
                .then()
                .statusCode(201);

        // Obter todas as reservas
        RestAssured.given()
                .port(port)
                .get("/reservations")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(1)));
    }
}
