package tqs.hw1.controller.integration.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.ReservationRepository;
import tqs.hw1.repository.RestaurantRepository;
import java.time.LocalDate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ReservationControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    public void resetDb() {
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} cria uma reserva")
    void whenBookMeal_thenReservationIsCreated() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("RestoTest"));
        Meal meal = mealRepository.save(new Meal("MealTest", LocalDate.now(), restaurant));

        mvc.perform(post("/reservations/book/" + meal.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.used", is(false)))
                .andExpect(jsonPath("$.meal.id", is(meal.getId().intValue())));
    }

    @Test
    @DisplayName("POST /reservations/book/{mealId} retorna erro 404 se a refeição não for encontrada")
    void whenMealNotFound_thenReturnNotFound() throws Exception {
        Long nonExistentMealId = 999L;

        mvc.perform(post("/reservations/book/" + nonExistentMealId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Meal not found")));
    }

    @Test
    @DisplayName("GET /reservations/{code} retorna a reserva quando o código é válido")
    void whenCheckReservation_thenReturnReservation() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("RestoTest"));
        Meal meal = mealRepository.save(new Meal("MealTest", LocalDate.now(), restaurant));
        Reservation reservation = reservationRepository.save(new Reservation("ABC123", LocalDate.now().atStartOfDay(), false, meal));

        mvc.perform(get("/reservations/" + reservation.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(reservation.getCode())))
                .andExpect(jsonPath("$.meal.id", is(meal.getId().intValue())));
    }

    @Test
    @DisplayName("GET /reservations lista todas as reservas")
    void whenGetReservations_thenReturnList() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("RestoTest"));
        Meal meal = mealRepository.save(new Meal("MealTest", LocalDate.now(), restaurant));
        reservationRepository.save(new Reservation("XYZ789", LocalDate.now().atStartOfDay(), false, meal));

        mvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} realiza o check-in corretamente")
    void whenCheckIn_thenReturnSuccess() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("RestoTest"));
        Meal meal = mealRepository.save(new Meal("MealTest", LocalDate.now(), restaurant));
        Reservation reservation = reservationRepository.save(new Reservation("DEF456", LocalDate.now().atStartOfDay(), false, meal));

        mvc.perform(post("/reservations/checkin/" + reservation.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Check-in successful"));
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} retorna erro 404 se a reserva não for encontrada ou já usada")
    void whenCheckInReservationNotFound_thenReturnNotFound() throws Exception {
        String invalidCode = "INVALID_CODE";

        mvc.perform(post("/reservations/checkin/" + invalidCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found or already used"));
    }

    @Test
    @DisplayName("POST /reservations/checkin/{code} retorna erro se a reserva já foi usada")
    void whenCheckInAlreadyUsed_thenReturnError() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("RestoTest"));
        Meal meal = mealRepository.save(new Meal("MealTest", LocalDate.now(), restaurant));
        Reservation reservation = reservationRepository.save(new Reservation("GHI789", LocalDate.now().atStartOfDay(), true, meal));

        mvc.perform(post("/reservations/checkin/" + reservation.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found or already used"));
    }
}
