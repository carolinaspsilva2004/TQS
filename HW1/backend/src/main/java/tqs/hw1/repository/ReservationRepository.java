package tqs.hw1.repository;

import tqs.hw1.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByCode(String code);
    long countByMeal_DateAndMeal_Restaurant_Id(LocalDate date, Long restaurantId);

}