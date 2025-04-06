package tqs.hw1.integration.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.model.Reservation;
import tqs.hw1.service.MealService;
import tqs.hw1.service.ReservationService;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
public class ReservationControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MealService mealService;

    @MockBean
    private ReservationService reservationService;

    private Meal meal;
    private Reservation reservation;

    @BeforeAll
    static void setupContainer() {
        container.start();
    }


    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");


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
    void whenBookMeal_thenReturnCreatedReservation() throws Exception {
        when(mealService.getMealById(anyLong())).thenReturn(Optional.of(meal));
        when(reservationService.createReservation(any(Meal.class))).thenReturn(reservation);

        mvc.perform(post("/reservations/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is("ABC123")));
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve retornar erro quando refeição não for encontrada")
    void whenBookMeal_thenReturnNotFound() throws Exception {
        when(mealService.getMealById(anyLong())).thenReturn(Optional.empty());

        mvc.perform(post("/reservations/book/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Meal not found")));
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar uma reserva válida")
    void whenCheckReservation_thenReturnReservation() throws Exception {
        when(reservationService.getReservationByCode("ABC123")).thenReturn(Optional.of(reservation));

        mvc.perform(get("/reservations/ABC123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("ABC123")));
    }

    @Test
    @DisplayName("GET /reservations/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckReservation_thenReturnNotFound() throws Exception {
        when(reservationService.getReservationByCode("XYZ999")).thenReturn(Optional.empty());

        mvc.perform(get("/reservations/XYZ999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve marcar check-in com sucesso")
    void whenCheckIn_thenReturnSuccess() throws Exception {
        when(reservationService.checkInReservation("ABC123")).thenReturn(true);

        mvc.perform(post("/reservations/checkin/ABC123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Check-in successful"));
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenCheckIn_thenReturnNotFound() throws Exception {
        when(reservationService.checkInReservation("XYZ999")).thenReturn(false);

        mvc.perform(post("/reservations/checkin/XYZ999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found or already used"));
    }

    @Test
    @DisplayName("GET /reservations - Deve retornar todas as reservas")
    void whenGetAllReservations_thenReturnReservationsList() throws Exception {
        when(reservationService.getReservations()).thenReturn(List.of(reservation));

        mvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is("ABC123")));
    }

    @Test
    @DisplayName("DELETE /reservations/{code} - Deve deletar uma reserva com sucesso")
    void whenDeleteReservation_thenReturnSuccess() throws Exception {
        when(reservationService.deleteReservationByCode("ABC123")).thenReturn(true);

        mvc.perform(delete("/reservations/ABC123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation deleted successfully"));
    }

    @Test
    @DisplayName("DELETE /reservations/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenDeleteReservation_thenReturnNotFound() throws Exception {
        when(reservationService.deleteReservationByCode("XYZ999")).thenReturn(false);

        mvc.perform(delete("/reservations/XYZ999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found"));
    }
}
