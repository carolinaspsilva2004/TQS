package tqs.hw1.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RestaurantTest {

    @Test
    void testRestaurantCreation() {
        Restaurant restaurant = new Restaurant("Testaurant");

        assertThat(restaurant.getName()).isEqualTo("Testaurant");    }

    @Test
    void testSetters() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("New Name");

        assertThat(restaurant.getName()).isEqualTo("New Name");
    }

    @Test
    void testToString() {
        Restaurant restaurant = new Restaurant("Testaurant");
        assertThat(restaurant.toString()).contains("Testaurant");
    }
}
