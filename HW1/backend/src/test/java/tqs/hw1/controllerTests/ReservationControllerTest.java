package tqs.hw1.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.hw1.controller.ReservationController;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.model.Restaurant;
import tqs.hw1.service.MealService;
import tqs.hw1.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MealService mealService;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Meal meal;
    private Reservation reservation;

    @BeforeEach
    void setup() {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        meal = new Meal("Meal Test", LocalDate.now(), restaurant);
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
    // Mocking the reservationService to return true when trying to delete the reservation
    when(reservationService.deleteReservationByCode("ABC123")).thenReturn(true);

    mvc.perform(delete("/reservations/ABC123")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Reservation deleted successfully"));
}

    @Test
    @DisplayName("DELETE /reservations/{code} - Deve retornar erro quando reserva não for encontrada")
    void whenDeleteReservation_thenReturnNotFound() throws Exception {
        // Mocking the reservationService to return false when trying to delete a non-existent reservation
        when(reservationService.deleteReservationByCode("XYZ999")).thenReturn(false);

        mvc.perform(delete("/reservations/XYZ999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found"));
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve retornar erro se o restaurante estiver cheio")
    void whenBookMealAndRestaurantIsFull_thenReturnBadRequest() throws Exception {
        when(mealService.getMealById(anyLong())).thenReturn(Optional.of(meal));
        // Mocka que já existem 10 reservas para aquele restaurante e data
        when(reservationService.countReservationsForMealOnDate(meal.getDate(), meal.getRestaurant().getId()))
                .thenReturn(50L);

        mvc.perform(post("/reservations/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Restaurant is fully booked for this date")));
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} - Deve retornar erro se a reserva já foi usada")
    void whenCheckInWithUsedReservation_thenReturnNotFound() throws Exception {

        // Reserva já foi usada
        when(reservationService.checkInReservation("USED123")).thenReturn(false);

        mvc.perform(post("/reservations/checkin/USED123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found or already used"));
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} - Deve retornar erro se não houver serviço naquele dia (meal não encontrada)")
    void whenBookMealWithInvalidMeal_thenReturnMealNotFound() throws Exception {
        when(mealService.getMealById(999L)).thenReturn(Optional.empty());

        mvc.perform(post("/reservations/book/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Meal not found")));
    }


}
