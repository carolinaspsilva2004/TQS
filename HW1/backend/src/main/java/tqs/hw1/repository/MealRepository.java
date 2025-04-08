package tqs.hw1.repository;

import tqs.hw1.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantIdAndDateAfter(Long restaurantId, LocalDate date);
    List<Meal> findByRestaurantId(Long restaurantId);
    List<Meal> findByDate(LocalDate date);
    Optional<Meal> findByIdAndRestaurantId(Long id, Long restaurantId);
}