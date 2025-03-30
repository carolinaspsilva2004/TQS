package tqs.hw1.service;

import org.springframework.stereotype.Service;
import tqs.hw1.model.Meal;
import tqs.hw1.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Optional<Meal> getMealById(Long id) {
        return mealRepository.findById(id);
    }

    public List<Meal> getMealsByRestaurantId(Long restaurantId) {
        return mealRepository.findByRestaurantId(restaurantId);
    }

    public List<Meal> getMealsByDate(LocalDate date) {
        return mealRepository.findByDate(date);
    }

    public Meal saveMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return mealRepository.existsById(id);
    }
}
