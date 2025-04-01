package tqs.hw1.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    void testReservationCreation() {
        Restaurant restaurant = new Restaurant("Testaurant");
        Meal meal = new Meal("Pasta Carbonara", LocalDate.now(), restaurant);
        Reservation reservation = new Reservation("ABC123", LocalDateTime.now(), false, meal);

        assertThat(reservation.getCode()).isEqualTo("ABC123");
        assertThat(reservation.getMeal()).isEqualTo(meal);
    }

    @Test
    void testSetters() {
        Reservation reservation = new Reservation();
        Meal meal = new Meal("Steak", LocalDate.now(), new Restaurant("Grill House"));
        LocalDateTime dateTime = LocalDateTime.of(2024, 3, 30, 18, 30);

        reservation.setCode("XYZ789");
        reservation.setReservationDate(dateTime);
        reservation.setUsed(true);
        reservation.setMeal(meal);

        assertThat(reservation.getCode()).isEqualTo("XYZ789");
        assertThat(reservation.getReservationDate()).isEqualTo(dateTime);
        assertThat(reservation.isUsed()).isTrue();
        assertThat(reservation.getMeal()).isEqualTo(meal);
    }

    @Test
    void testToString() {
        Meal meal = new Meal("Pasta Carbonara", LocalDate.now(), new Restaurant("Testaurant"));
        Reservation reservation = new Reservation("ABC123", LocalDateTime.now(), false, meal);

        assertThat(reservation.toString()).contains("ABC123");
        assertThat(reservation.toString()).contains("Pasta Carbonara");
    }
}
