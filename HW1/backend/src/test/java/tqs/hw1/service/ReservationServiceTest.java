package tqs.hw1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.repository.ReservationRepository;
import tqs.hw1.service.ReservationService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateReservation() {
        Meal meal = new Meal("Steak", LocalDate.now(), null);
        Reservation savedReservation = new Reservation(UUID.randomUUID().toString(), LocalDate.now().atStartOfDay(), false, meal);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        Reservation reservation = reservationService.createReservation(meal);
        assertThat(reservation.getMeal().getDescription()).isEqualTo("Steak");
        assertThat(reservation.isUsed()).isFalse();
    }

    @Test
    void shouldCheckInReservation() {
        Reservation reservation = new Reservation("CHECK123", LocalDate.now().atStartOfDay(), false, null);
        when(reservationRepository.findByCode("CHECK123")).thenReturn(Optional.of(reservation));

        boolean checkedIn = reservationService.checkInReservation("CHECK123");
        assertThat(checkedIn).isTrue();
        verify(reservationRepository).save(reservation);
    }

    @Test
    void shouldReturnFalseIfReservationCodeNotFoundOnCheckIn() {
        when(reservationRepository.findByCode("INVALID")).thenReturn(Optional.empty());
        boolean checkedIn = reservationService.checkInReservation("INVALID");
        assertThat(checkedIn).isFalse();
    }
}
