package tqs.hw1.controller;

import tqs.hw1.model.Meal;
import tqs.hw1.repository.MealRepository;
import org.springframework.web.bind.annotation.*;
import tqs.hw1.model.Restaurant;
import tqs.hw1.service.MealService;
import tqs.hw1.service.ExternalMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {
    private final MealService mealService;
    private final ExternalMenuService externalMenuService;

    public MealController(MealService mealService, ExternalMenuService externalMenuService) {
        this.mealService = mealService;
        this.externalMenuService = externalMenuService;
    }

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{id}")
    public Meal getMealById(@PathVariable Long id) {
        return mealService.getMealById(id)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada com id: " + id));
    }

    @PostMapping("/fetch")
    public String fetchMealsFromExternal(@RequestBody Restaurant restaurant) {
        externalMenuService.fetchAndSaveMealsForRestaurant(restaurant);
        return "Refeições buscadas e salvas com sucesso.";
    }
}
