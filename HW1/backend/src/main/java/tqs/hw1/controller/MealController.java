package tqs.hw1.controller;

import tqs.hw1.model.Meal;
import tqs.hw1.repository.MealRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {
    private final MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }
}