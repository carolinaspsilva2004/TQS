package tqs.hw1.integration.restTemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.model.Reservation;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.ReservationRepository;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.flywaydb.core.Flyway;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ReservationControllerRestTemplateIT {

    private static final Logger logger = LoggerFactory.getLogger(ReservationControllerRestTemplateIT.class);

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MealRepository mealRepository;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    private Meal meal;
    private Reservation reservation;

    @BeforeAll
    static void setupContainer() {
        logger.info("Starting PostgreSQL container...");
        container.start();
        Flyway flyway = Flyway.configure()
            .dataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword())
            .locations("classpath:db/migration")
            .load();
        flyway.migrate();
        logger.info("Database migration complete.");
    }

    @BeforeEach
    void setup() {
        logger.info("Setting up test data...");
        Restaurant restaurant = new Restaurant("Test Restaurant");

        meal = new Meal("Meal Test", LocalDateTime.now().toLocalDate(), restaurant);
        meal = mealRepository.save(meal);  

        String uniqueCode = UUID.randomUUID().toString();

        reservation = new Reservation(uniqueCode, LocalDateTime.now(), false, meal);
        reservation = reservationRepository.save(reservation);  // Saving reservation to the database

        logger.info("Test data setup complete with meal ID: {} and reservation code: {}", meal.getId(), reservation.getCode());
    }

    @AfterEach
    void tearDown() {
        logger.info("Tearing down test data...");
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
        logger.info("Test data cleaned up.");
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve criar uma reserva com sucesso")
    void whenBookMeal_thenReturnCreatedReservation() {
        logger.info("Testing POST /reservations/book/{mealId}...");
        String url = "http://localhost:" + randomServerPort + "/reservations/book/" + meal.getId();

        ResponseEntity<Reservation> response = testRestTemplate.postForEntity(url, null, Reservation.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCode()).isNotNull();  // Verifies that the code is not null
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve retornar erro quando refeição não for encontrada")
    void whenBookMeal_thenReturnNotFound() {
        logger.info("Testing POST /reservations/book/{mealId} for non-existent meal...");
        String url = "http://localhost:" + randomServerPort + "/reservations/book/999";

        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Meal not found");
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar uma reserva válida")
    void whenCheckReservation_thenReturnReservation() {
        logger.info("Testing GET /reservations/{code}...");
        String url = "http://localhost:" + randomServerPort + "/reservations/" + reservation.getCode();

        ResponseEntity<Reservation> response = testRestTemplate.getForEntity(url, Reservation.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCode()).isEqualTo(reservation.getCode());
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckReservation_thenReturnNotFound() {
        logger.info("Testing GET /reservations/{code} for non-existent reservation...");
        String url = "http://localhost:" + randomServerPort + "/reservations/XYZ999";

        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve marcar check-in com sucesso")
    void whenCheckIn_thenReturnSuccess() {
        logger.info("Testing POST /reservations/checkin/{code}...");
        String url = "http://localhost:" + randomServerPort + "/reservations/checkin/" + reservation.getCode();

        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Check-in successful");
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckIn_thenReturnNotFound() {
        logger.info("Testing POST /reservations/checkin/{code} for non-existent reservation...");
        String url = "http://localhost:" + randomServerPort + "/reservations/checkin/XYZ999";

        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Reservation not found or already used");
    }

    @Test
    @DisplayName("GET /reservations - Deve retornar todas as reservas")
    void whenGetAllReservations_thenReturnReservationsList() {
        logger.info("Testing GET /reservations...");
        String url = "http://localhost:" + randomServerPort + "/reservations";

        ResponseEntity<List> response = testRestTemplate.exchange(url, org.springframework.http.HttpMethod.GET, null, List.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1); // Verifies there is 1 reservation
    }

    @Test
    @DisplayName("DELETE /reservations/{code} - Deve deletar uma reserva com sucesso")
    void whenDeleteReservation_thenReturnSuccess() {
        logger.info("Testing DELETE /reservations/{code}...");
        String url = "http://localhost:" + randomServerPort + "/reservations/" + reservation.getCode();

        ResponseEntity<String> response = testRestTemplate.exchange(url, org.springframework.http.HttpMethod.DELETE, null, String.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Reservation deleted successfully");
    }

    @Test
    @DisplayName("DELETE /reservations/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenDeleteReservation_thenReturnNotFound() {
        logger.info("Testing DELETE /reservations/{code} for non-existent reservation...");
        String url = "http://localhost:" + randomServerPort + "/reservations/XYZ999";

        ResponseEntity<String> response = testRestTemplate.exchange(url, org.springframework.http.HttpMethod.DELETE, null, String.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Reservation not found");
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve retornar erro se restaurante estiver lotado")
    void whenRestaurantIsFullyBooked_thenReturnBadRequest() {
        logger.info("Testing POST /reservations/book/{mealId} when restaurant is fully booked...");
        for (int i = 0; i < 50; i++) {
            Reservation r = new Reservation(UUID.randomUUID().toString(), LocalDateTime.now(), false, meal);
            reservationRepository.save(r);
        }

        String url = "http://localhost:" + randomServerPort + "/reservations/book/" + meal.getId();

        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        logger.info("Received response status: {}", response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Restaurant is fully booked for this date");
    }

}
