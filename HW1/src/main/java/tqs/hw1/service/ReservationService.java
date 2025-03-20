package tqs.hw1.service;

import tqs.hw1.model.*;
import tqs.hw1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(Meal meal) {
        Reservation reservation = new Reservation();
        reservation.setCode(UUID.randomUUID().toString());
        reservation.setMeal(meal);
        reservation.setUsed(false);
        reservation.setReservationDate(LocalDate.now().atStartOfDay());
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationByCode(String code) {
        return reservationRepository.findByCode(code);
    }

    public boolean checkInReservation(String code) {
        return reservationRepository.findByCode(code).map(res -> {
            res.setUsed(true);
            reservationRepository.save(res);
            return true;
        }).orElse(false);
    }
}
