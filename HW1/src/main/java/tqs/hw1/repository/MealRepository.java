package tqs.hw1.repository;

import com.moliceirouniversity.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantIdAndDateAfter(Long restaurantId, LocalDate date);
}