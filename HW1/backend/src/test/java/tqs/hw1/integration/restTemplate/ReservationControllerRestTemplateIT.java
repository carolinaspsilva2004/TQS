package tqs.hw1.integration.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.model.Reservation;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class ReservationControllerRestTemplateIT {

    @Autowired
    private RestTemplate restTemplate;

    private Meal meal;
    private Reservation reservation;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @BeforeAll
    static void setupContainer() {
        container.start();
    }

    @BeforeEach
    void setup() {
        // Criando um restaurante de exemplo
        Restaurant restaurant = new Restaurant("Test Restaurant");

        // Associando o restaurante à refeição
        meal = new Meal("Meal Test", LocalDateTime.now().toLocalDate(), restaurant);

        // Criando uma reserva com a refeição
        reservation = new Reservation("ABC123", LocalDateTime.now(), false, meal);
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve criar uma reserva com sucesso")
    void whenBookMeal_thenReturnCreatedReservation() {
        String url = "http://localhost:8080/reservations/book/1";
    
        // Simulando a resposta esperada da criação
        ResponseEntity<Reservation> response = restTemplate.postForEntity(url, null, Reservation.class);
    
        // Verificando se a resposta é 201 Created e contém o código da reserva
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody().getCode()).isEqualTo("ABC123");
    }
    

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve retornar erro quando refeição não for encontrada")
    void whenBookMeal_thenReturnNotFound() {
        // Endpoint com um ID de refeição inexistente
        String url = "http://localhost:8080/reservations/book/999";

        // Realizando a requisição POST e verificando o erro 404 Not Found
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).contains("Meal not found");
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar uma reserva válida")
    void whenCheckReservation_thenReturnReservation() {
        // Endpoint para buscar uma reserva
        String url = "http://localhost:8080/reservations/ABC123";

        // Realizando a requisição GET e verificando a resposta
        ResponseEntity<Reservation> response = restTemplate.getForEntity(url, Reservation.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getCode()).isEqualTo("ABC123");
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckReservation_thenReturnNotFound() {
        // Endpoint com código de reserva inexistente
        String url = "http://localhost:8080/reservations/XYZ999";

        // Realizando a requisição GET e verificando o erro 404 Not Found
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve marcar check-in com sucesso")
    void whenCheckIn_thenReturnSuccess() {
        // Endpoint para realizar check-in na reserva
        String url = "http://localhost:8080/reservations/checkin/ABC123";

        // Simulando a resposta de sucesso
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Check-in successful");
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckIn_thenReturnNotFound() {
        // Endpoint para check-in com código de reserva inexistente
        String url = "http://localhost:8080/reservations/checkin/XYZ999";

        // Realizando a requisição POST e verificando erro
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).contains("Reservation not found or already used");
    }

    @Test
    @DisplayName("GET /reservations - Deve retornar todas as reservas")
    void whenGetAllReservations_thenReturnReservationsList() {
        // Endpoint para buscar todas as reservas
        String url = "http://localhost:8080/reservations";

        // Realizando a requisição GET e verificando a lista de reservas
        ResponseEntity<List> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, null, List.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1); // Verifica se há 1 reserva
    }
}
