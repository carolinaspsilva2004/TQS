package tqs.hw1.integration.mock;

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
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.ReservationRepository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
public class CheckInMockIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MealRepository mealRepository;

    @AfterEach
    public void resetDb() {
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.2")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");


    @Test
    @DisplayName("POST /checkin/{code} returns true for valid code")
    void whenCheckInValidReservation_thenReturnsTrue() throws Exception {
        Meal meal = mealRepository.save(new Meal());

        Reservation reservation = new Reservation();
        reservation.setCode("ABC123");
        reservation.setMeal(meal);
        reservation.setUsed(false);
        reservation.setReservationDate(LocalDate.now().atStartOfDay());
        reservationRepository.save(reservation);

        mvc.perform(post("/checkin/ABC123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(is("true")));
    }

    @Test
    @DisplayName("POST /checkin/{code} returns false for invalid code")
    void whenCheckInInvalidReservation_thenReturnsFalse() throws Exception {
        mvc.perform(post("/checkin/INVALIDCODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(is("false")));
    }
}
