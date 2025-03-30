package tqs.hw1.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RestaurantTest {

    @Test
    void testRestaurantCreation() {
        Restaurant restaurant = new Restaurant("Testaurant", "http://menu.url");

        assertThat(restaurant.getName()).isEqualTo("Testaurant");
        assertThat(restaurant.getExternalMenuUrl()).isEqualTo("http://menu.url");
    }

    @Test
    void testSetters() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("New Name");
        restaurant.setExternalMenuUrl("http://new.menu");

        assertThat(restaurant.getName()).isEqualTo("New Name");
        assertThat(restaurant.getExternalMenuUrl()).isEqualTo("http://new.menu");
    }

    @Test
    void testToString() {
        Restaurant restaurant = new Restaurant("Testaurant", "http://menu.url");
        assertThat(restaurant.toString()).contains("Testaurant");
        assertThat(restaurant.toString()).contains("http://menu.url");
    }
}
