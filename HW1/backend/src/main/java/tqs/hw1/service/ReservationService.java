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
    private static final int MAX_RESERVATIONS_PER_DAY = 10;


    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Meal meal) {
        LocalDate date = meal.getDate();
        Restaurant restaurant = meal.getRestaurant();
    
        // Conta quantas reservas já existem para este restaurante e esta data
        long existingReservations = reservationRepository
            .countByMeal_DateAndMeal_Restaurant_Id(date, restaurant.getId());
    
        if (existingReservations >= MAX_RESERVATIONS_PER_DAY) {
                throw new IllegalStateException("Restaurant is fully booked for this date");
        }
            
    
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
        Reservation reservation = reservationRepository.findByCode(code)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    
        if (reservation.isUsed()) {
            throw new IllegalStateException("Reservation has already been used");
        }
    
        reservation.setUsed(true);
        reservationRepository.save(reservation);
        return true;
    }
    
    

    public boolean deleteReservationByCode(String code) {
        Optional<Reservation> reservation = reservationRepository.findByCode(code);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return true;
        }
        return false;
    }
    
    public long countReservationsForMealOnDate(LocalDate date, Long restaurantId) {
        return reservationRepository.countByMeal_DateAndMeal_Restaurant_Id(date, restaurantId);
    }
}
