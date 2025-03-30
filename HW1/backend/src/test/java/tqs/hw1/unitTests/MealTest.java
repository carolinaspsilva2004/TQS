package tqs.hw1.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class MealTest {

    @Test
    void testMealCreation() {
        Restaurant restaurant = new Restaurant("Testaurant", "http://menu.url");
        Meal meal = new Meal("Pasta Carbonara", LocalDate.now(), restaurant);

        assertThat(meal.getDescription()).isEqualTo("Pasta Carbonara");
        assertThat(meal.getDate()).isEqualTo(LocalDate.now());
        assertThat(meal.getRestaurant()).isEqualTo(restaurant);
    }

    @Test
    void testSetters() {
        Meal meal = new Meal();
        Restaurant restaurant = new Restaurant("New Place", "http://new.menu");
        LocalDate date = LocalDate.of(2024, 3, 30);

        meal.setDescription("New Meal");
        meal.setDate(date);
        meal.setRestaurant(restaurant);

        assertThat(meal.getDescription()).isEqualTo("New Meal");
        assertThat(meal.getDate()).isEqualTo(date);
        assertThat(meal.getRestaurant()).isEqualTo(restaurant);
    }

    @Test
    void testToString() {
        Restaurant restaurant = new Restaurant("Testaurant", "http://menu.url");
        Meal meal = new Meal("Pasta Carbonara", LocalDate.now(), restaurant);

        assertThat(meal.toString()).contains("Pasta Carbonara");
        assertThat(meal.toString()).contains("Testaurant");
    }
}