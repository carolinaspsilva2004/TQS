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
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Testaurant"));
        Meal meal = new Meal("Pasta Carbonara", LocalDate.now(), restaurant);
        mealRepository.save(meal);

        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(1);
        assertThat(meals.get(0).getDescription()).isEqualTo("Pasta Carbonara");
    }

    @Test
    void shouldFindMealsByRestaurantAndFutureDate() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("FutureFood"));
        mealRepository.save(new Meal("Meal Today", LocalDate.now(), restaurant));
        mealRepository.save(new Meal("Meal Tomorrow", LocalDate.now().plusDays(1), restaurant));

        List<Meal> futureMeals = mealRepository.findByRestaurantIdAndDateAfter(restaurant.getId(), LocalDate.now());
        assertThat(futureMeals).hasSize(1);
        assertThat(futureMeals.get(0).getDescription()).isEqualTo("Meal Tomorrow");
    }

    @Test
    void shouldFindMealsByRestaurantId() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Testaurant"));
        mealRepository.save(new Meal("Pasta", LocalDate.now(), restaurant));
        mealRepository.save(new Meal("Pizza", LocalDate.now(), restaurant));

        List<Meal> meals = mealRepository.findByRestaurantId(restaurant.getId());
        assertThat(meals).hasSize(2);
        assertThat(meals).extracting(Meal::getDescription).containsExactlyInAnyOrder("Pasta", "Pizza");
    }

    @Test
    void shouldFindMealsByDate() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Testaurant"));
        LocalDate today = LocalDate.now();
        mealRepository.save(new Meal("Burger", today, restaurant));
        mealRepository.save(new Meal("Salad", today, restaurant));
        mealRepository.save(new Meal("Pasta", today.plusDays(1), restaurant));

        List<Meal> meals = mealRepository.findByDate(today);
        assertThat(meals).hasSize(2);
        assertThat(meals).extracting(Meal::getDescription).containsExactlyInAnyOrder("Burger", "Salad");
    }
}
