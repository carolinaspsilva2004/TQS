package tqs.hw1.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.hw1.model.*;
import tqs.hw1.repository.ReservationRepository;
import tqs.hw1.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste de reserva criada corretamente
    @Test
    void shouldCreateReservation() {
        Restaurant restaurant = new Restaurant("Rest A");
        Meal meal = new Meal("Fish & Chips", LocalDate.now(), restaurant);
        Reservation savedReservation = new Reservation(UUID.randomUUID().toString(), LocalDate.now().atStartOfDay(), false, meal);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        Reservation reservation = reservationService.createReservation(meal);
        assertThat(reservation.getMeal().getDescription()).isEqualTo("Fish & Chips");
        assertThat(reservation.isUsed()).isFalse();
    }

    // Teste de verificação de reserva (check-in)
    @Test
    void shouldCheckInReservation() {
        Reservation reservation = new Reservation("CHECK123", LocalDate.now().atStartOfDay(), false, null);
        when(reservationRepository.findByCode("CHECK123")).thenReturn(Optional.of(reservation));

        boolean checkedIn = reservationService.checkInReservation("CHECK123");
        assertThat(checkedIn).isTrue();
        verify(reservationRepository).save(reservation);
    }

    // Teste para verificar se o ticket já foi utilizado
    @Test
    void shouldRejectCheckInIfReservationAlreadyUsed() {
        Reservation reservation = new Reservation("CHECK123", LocalDate.now().atStartOfDay(), true, null); // Já usado
        when(reservationRepository.findByCode("CHECK123")).thenReturn(Optional.of(reservation));
    
        assertThrows(IllegalStateException.class, () -> {
            reservationService.checkInReservation("CHECK123");
        });
    
        verify(reservationRepository, never()).save(any(Reservation.class)); // Garante que não tentou salvar
    }


    // Teste para garantir que a reserva não é permitida se o limite de capacidade foi atingido
    @Test
    void shouldNotAllowReservationIfRestaurantIsFull() {
        Restaurant restaurant = new Restaurant("Rest A");
        restaurant.setId(1L);
        Meal meal = new Meal("Steak", LocalDate.now(), restaurant);

        when(reservationRepository.countByMeal_DateAndMeal_Restaurant_Id(LocalDate.now(), 1L)).thenReturn(10L);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.createReservation(meal);
        });

        verify(reservationRepository, never()).save(any());
    }


    // Teste para garantir que a reserva não seja feita em um dia sem serviço (sem refeições)
    @Test
    void shouldNotAllowReservationOnDayWithoutService() {
        Restaurant restaurant = new Restaurant("Rest A");
        restaurant.setId(2L);
        Meal meal = new Meal("Pasta", LocalDate.now().plusDays(1), restaurant);

        when(reservationRepository.countByMeal_DateAndMeal_Restaurant_Id(meal.getDate(), 2L)).thenReturn(10L);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.createReservation(meal);
        });

        verify(reservationRepository, never()).save(any());
    }

    // Teste para garantir que a deleção de reserva funciona corretamente
    @Test
    void shouldDeleteReservation() {
        Reservation reservation = new Reservation("DELETE123", LocalDate.now().atStartOfDay(), false, null);
        when(reservationRepository.findByCode("DELETE123")).thenReturn(Optional.of(reservation));
        doNothing().when(reservationRepository).delete(any(Reservation.class));

        boolean deleted = reservationService.deleteReservationByCode("DELETE123");
        assertThat(deleted).isTrue();
        verify(reservationRepository).delete(reservation); // Verificando se o delete foi chamado no repositório
    }

    // Teste para garantir que não é possível deletar uma reserva que não existe
    @Test
    void shouldReturnFalseIfReservationCodeNotFoundOnDelete() {
        when(reservationRepository.findByCode("INVALID")).thenReturn(Optional.empty());
        boolean deleted = reservationService.deleteReservationByCode("INVALID");
        assertThat(deleted).isFalse();
        verify(reservationRepository, never()).delete(any(Reservation.class));
    }
}
