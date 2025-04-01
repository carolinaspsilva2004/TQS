package tqs.hw1.integration.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
public class ReservationControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeAll
    static void setupContainer() {
        container.start();
    }

    @AfterEach
    public void resetDb() {
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @Test
    @DisplayName("POST /reservations/book/{mealId} cria uma reserva com sucesso")
    void whenBookMeal_thenCreateReservation() throws Exception {
        Meal meal = new Meal("Pizza", LocalDate.now(), null);
        meal = mealRepository.save(meal);

        mvc.perform(post("/reservations/book/{mealId}", meal.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meal.id", is(meal.getId().intValue())));
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} retorna erro quando a refeição não existe")
    void whenBookNonExistentMeal_thenReturnError() throws Exception {
        mvc.perform(post("/reservations/book/{mealId}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /reservations retorna todas as reservas")
    void whenGetAllReservations_thenReturnReservationList() throws Exception {
        Meal meal = new Meal("Sushi", LocalDate.now(), null);
        meal = mealRepository.save(meal);
        Reservation reservation = new Reservation("ABC123", LocalDateTime.now(), false, meal);
        reservationRepository.save(reservation);

        mvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("GET /reservations/{code} retorna uma reserva válida")
    void whenCheckReservation_thenReturnReservation() throws Exception {
        Meal meal = new Meal("Pasta", LocalDate.now(), null);
        meal = mealRepository.save(meal);
        Reservation reservation = new Reservation("XYZ789", LocalDateTime.now(), false, meal);
        reservationRepository.save(reservation);

        mvc.perform(get("/reservations/{code}", "XYZ789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("XYZ789")));
    }

    @Test
    @DisplayName("GET /reservations/{code} retorna erro quando a reserva não existe")
    void whenCheckNonExistentReservation_thenReturnError() throws Exception {
        mvc.perform(get("/reservations/{code}", "INVALID123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} realiza check-in com sucesso")
    void whenCheckInReservation_thenReturnSuccess() throws Exception {
        Meal meal = new Meal("Burger", LocalDate.now(), null);
        meal = mealRepository.save(meal);
        Reservation reservation = new Reservation("CHECKIN123", LocalDateTime.now(), false, meal);
        reservationRepository.save(reservation);

        mvc.perform(post("/reservations/checkin/{code}", "CHECKIN123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Check-in successful"));
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} retorna erro quando a reserva não existe")
    void whenCheckInNonExistentReservation_thenReturnError() throws Exception {
        mvc.perform(post("/reservations/checkin/{code}", "FAKECODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found or already used"));
    }
}