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
    @DisplayName("POST /reservations/book/{mealId} creates a reservation")
    void whenBookMeal_thenReservationIsCreated() throws Exception {
        // Criar um restaurante
        Restaurant restaurant = restaurantRepository.save(new Restaurant("RestoTest", "Lisbon", "http://fakeurl.com"));

        LocalDate date = LocalDate.parse("2025-03-25"); // exemplo de como criar LocalDate

        // Criar uma refeição associada ao restaurante
        Meal meal = mealRepository.save(new Meal("MealTest", date, restaurant));

        // Fazer a reserva
        mvc.perform(post("/reservations/book/" + meal.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.used", is(false)))
                .andExpect(jsonPath("$.meal.id", is(meal.getId().intValue())));
    }
}
