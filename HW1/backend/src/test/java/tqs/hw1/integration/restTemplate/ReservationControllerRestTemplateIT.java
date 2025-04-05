package tqs.hw1.integration.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
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

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ReservationControllerRestTemplateIT {

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

    @BeforeEach
    void setup() {
        // Criando um restaurante de exemplo
        Restaurant restaurant = new Restaurant("Test Restaurant");

        // Criando a refeição associada ao restaurante
        meal = new Meal("Meal Test", LocalDateTime.now().toLocalDate(), restaurant);
        meal = mealRepository.save(meal);  // Salvando a refeição no banco de dados

        // Gerando um código único para a reserva utilizando UUID
        String uniqueCode = UUID.randomUUID().toString();

        // Criando uma reserva com a refeição e código único
        reservation = new Reservation(uniqueCode, LocalDateTime.now(), false, meal);
        reservation = reservationRepository.save(reservation);  // Salvando a reserva no banco
    }

    @AfterEach
    void tearDown() {
        // Limpando os dados após cada teste
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve criar uma reserva com sucesso")
    void whenBookMeal_thenReturnCreatedReservation() {
        String url = "http://localhost:" + randomServerPort + "/reservations/book/" + meal.getId();

        // Simulando a resposta esperada da criação
        ResponseEntity<Reservation> response = testRestTemplate.postForEntity(url, null, Reservation.class);

        // Verificando se a resposta é 201 Created e contém o código da reserva
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCode()).isNotNull();  // Verifica que o código não é nulo
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve retornar erro quando refeição não for encontrada")
    void whenBookMeal_thenReturnNotFound() {
        // Endpoint com um ID de refeição inexistente
        String url = "http://localhost:" + randomServerPort + "/reservations/book/999";

        // Realizando a requisição POST e verificando o erro 404 Not Found
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Meal not found");
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar uma reserva válida")
    void whenCheckReservation_thenReturnReservation() {
        // Endpoint para buscar uma reserva
        String url = "http://localhost:" + randomServerPort + "/reservations/" + reservation.getCode();

        // Realizando a requisição GET e verificando a resposta
        ResponseEntity<Reservation> response = testRestTemplate.getForEntity(url, Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCode()).isEqualTo(reservation.getCode());
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckReservation_thenReturnNotFound() {
        // Endpoint com código de reserva inexistente
        String url = "http://localhost:" + randomServerPort + "/reservations/XYZ999";

        // Realizando a requisição GET e verificando o erro 404 Not Found
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve marcar check-in com sucesso")
    void whenCheckIn_thenReturnSuccess() {
        // Endpoint para realizar check-in na reserva
        String url = "http://localhost:" + randomServerPort + "/reservations/checkin/" + reservation.getCode();

        // Simulando a resposta de sucesso
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Check-in successful");
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckIn_thenReturnNotFound() {
        // Endpoint para check-in com código de reserva inexistente
        String url = "http://localhost:" + randomServerPort + "/reservations/checkin/XYZ999";

        // Realizando a requisição POST e verificando erro
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Reservation not found or already used");
    }

    @Test
    @DisplayName("GET /reservations - Deve retornar todas as reservas")
    void whenGetAllReservations_thenReturnReservationsList() {
        // Endpoint para buscar todas as reservas
        String url = "http://localhost:" + randomServerPort + "/reservations";

        // Realizando a requisição GET e verificando a lista de reservas
        ResponseEntity<List> response = testRestTemplate.exchange(url, org.springframework.http.HttpMethod.GET, null, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1); // Verifica se há 1 reserva
    }
}
