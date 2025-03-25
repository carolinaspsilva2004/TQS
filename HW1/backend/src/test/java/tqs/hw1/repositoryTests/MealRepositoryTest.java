package tqs.hw1.repositoryTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MealRepositoryTest {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void shouldSaveAndRetrieveMeal() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Testaurant", "City", "http://menu.url"));
        Meal meal = new Meal("Pasta Carbonara", LocalDate.now(), restaurant);
        mealRepository.save(meal);

        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(1);
        assertThat(meals.get(0).getDescription()).isEqualTo("Pasta Carbonara");
    }

    @Test
    void shouldFindMealsByRestaurantAndFutureDate() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("FutureFood", "FutureCity", "http://future.url"));
        mealRepository.save(new Meal("Meal Today", LocalDate.now(), restaurant));
        mealRepository.save(new Meal("Meal Tomorrow", LocalDate.now().plusDays(1), restaurant));

        List<Meal> futureMeals = mealRepository.findByRestaurantIdAndDateAfter(restaurant.getId(), LocalDate.now());
        assertThat(futureMeals).hasSize(1);
        assertThat(futureMeals.get(0).getDescription()).isEqualTo("Meal Tomorrow");
    }
}
