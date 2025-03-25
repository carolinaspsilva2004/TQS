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
import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import tqs.hw1.repository.MealRepository;

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

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MealRepository mealRepository;


    @Test
    void whenCheckInReservation_thenReturnTrue() {
        String code = "ABC123"; 
        Meal meal = new Meal(); 
        meal = mealRepository.save(meal); 
        Reservation reservation = new Reservation();
        reservation.setCode(code);
        reservation.setMeal(meal);
        reservation.setUsed(false);
        reservation.setReservationDate(LocalDate.now().atStartOfDay());
        
        reservationRepository.save(reservation);

        RestAssured.given()
                .port(port)
                .post("/checkin/" + code)
                .then()
                .statusCode(200)
                .body(is("true"));
    }


    @Test
    void whenCheckInReservation_thenReturnFalse() {
        String code = "INVALIDCODE"; 

        RestAssured.given()
                .port(port)
                .post("/checkin/" + code)
                .then()
                .statusCode(200)
                .body(is("false"));
    }
}
