package tqs.hw1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.hw1.model.Meal;
import tqs.hw1.service.MealService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import tqs.hw1.model.Restaurant;
import tqs.hw1.service.RestaurantService;
@RestController
@RequestMapping("/meals")
public class MealController {
    private final MealService mealService;
    private final RestaurantService restaurantService;

    public MealController(MealService mealService, RestaurantService restaurantService) {
        this.mealService = mealService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
        return mealService.getMealById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Meal>> getMealsByRestaurantId(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(mealService.getMealsByRestaurantId(restaurantId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Meal>> getMealsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(mealService.getMealsByDate(localDate));
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        try {
            if (meal.getRestaurant() == null || meal.getRestaurant().getId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Buscar o restaurante completo do BD
            Optional<Restaurant> restaurant = restaurantService.getRestaurantById(meal.getRestaurant().getId());
            if (restaurant.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Atribuir o restaurante carregado à meal
            meal.setRestaurant(restaurant.get());

            return ResponseEntity.ok(mealService.saveMeal(meal));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        if (mealService.existsById(id)) {
            mealService.deleteMeal(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
