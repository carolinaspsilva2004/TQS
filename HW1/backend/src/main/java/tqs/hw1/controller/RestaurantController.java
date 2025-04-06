package tqs.hw1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.service.MealService;
import tqs.hw1.service.RestaurantService;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final MealService mealService;

    public RestaurantController(RestaurantService restaurantService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado com id: " + id));
    }

    @PostMapping("/add")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        return new ResponseEntity<>(restaurantService.saveRestaurant(restaurant), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        if (!restaurantService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado.");
        }
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok("Restaurante removido com sucesso.");
    }

    @GetMapping("/{id}/meals")
    public ResponseEntity<List<Meal>> getMeals(@PathVariable Long id) {
        if (!restaurantService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado com id: " + id);
        }
        List<Meal> meals = mealService.getMealsByRestaurantId(id);
        List<Meal> result = new ArrayList<>();
    
        for (Meal meal : meals) {
            if (meal.getDate().isAfter(LocalDate.now().minusDays(1))) {
                result.add(meal);
            }
        }
        return ResponseEntity.ok(result);
    }
    
}
