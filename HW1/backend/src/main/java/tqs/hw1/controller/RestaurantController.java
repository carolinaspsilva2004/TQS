package tqs.hw1.controller;

import tqs.hw1.model.*;
import tqs.hw1.repository.*;
import tqs.hw1.service.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;
    private final WeatherService weatherService;
    private final ExternalMenuService externalMenuService;

    public RestaurantController(RestaurantRepository restaurantRepository, MealRepository mealRepository, WeatherService weatherService, ExternalMenuService externalMenuService) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
        this.weatherService = weatherService;
        this.externalMenuService = externalMenuService;
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @PostMapping
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }


    @PostMapping("/{id}/updateMeals")
    public String updateMeals(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        externalMenuService.fetchAndSaveMealsForRestaurant(restaurant);
        return "Meals updated from external source.";
    }

    @GetMapping("/{id}/meals")
    public List<Map<String, Object>> getMeals(@PathVariable Long id) {
        List<Meal> meals = mealRepository.findByRestaurantIdAndDateAfter(id, LocalDate.now().minusDays(1));
        List<Map<String, Object>> result = new ArrayList<>();

        for (Meal meal : meals) {
            Map<String, Object> mealInfo = new HashMap<>();
            try {
                mealInfo.put("meal", meal);
                mealInfo.put("weather", weatherService.getForecast(meal.getRestaurant().getLocation(), meal.getDate().toString()));
            } catch (Exception e) {
                mealInfo.put("weather", "Erro ao obter previsão do tempo");
                e.printStackTrace();
            }
            result.add(mealInfo);
        }
        return result;
    }

}
