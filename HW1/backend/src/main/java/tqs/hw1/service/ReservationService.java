package tqs.hw1.service;

import tqs.hw1.model.*;
import tqs.hw1.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

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

    public boolean deleteReservationByCode(String code) {
        Optional<Reservation> reservation = reservationRepository.findByCode(code);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return true;
        }
        return false;
    }
    
}
