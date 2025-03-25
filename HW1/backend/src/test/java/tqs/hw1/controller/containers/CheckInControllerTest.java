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

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CheckInControllerTest {

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
    void whenCheckInReservation_thenReturnTrue() {
        String code = "ABC123"; // Substitua com um código válido de reserva

        RestAssured.given()
                .port(port)
                .post("/checkin/" + code)
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    void whenCheckInReservation_thenReturnFalse() {
        String code = "INVALIDCODE"; // Substitua com um código inválido de reserva

        RestAssured.given()
                .port(port)
                .post("/checkin/" + code)
                .then()
                .statusCode(200)
                .body(is("false"));
    }
}
