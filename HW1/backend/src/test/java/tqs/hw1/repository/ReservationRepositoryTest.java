package tqs.hw1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.ReservationRepository;
import tqs.hw1.repository.RestaurantRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void shouldSaveAndRetrieveReservationByCode() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Rest A", "Loc A", "http://a.com"));
        Meal meal = mealRepository.save(new Meal("Fish & Chips", LocalDate.now(), restaurant));
        Reservation reservation = new Reservation("ABC123", LocalDateTime.now(), false, meal);
        reservationRepository.save(reservation);

        Optional<Reservation> found = reservationRepository.findByCode("ABC123");
        assertThat(found).isPresent();
        assertThat(found.get().getMeal().getDescription()).isEqualTo("Fish & Chips");
    }

    @Test
    void shouldReturnEmptyIfReservationCodeNotFound() {
        Optional<Reservation> found = reservationRepository.findByCode("NOT_FOUND");
        assertThat(found).isEmpty();
    }
}
