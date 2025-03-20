package tqs.hw1.repository;

import tqs.hw1.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantIdAndDateAfter(Long restaurantId, LocalDate date);
}