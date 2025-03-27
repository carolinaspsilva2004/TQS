package tqs.hw1.controller.integration.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.RestaurantRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RestaurantControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setup() {
        // Reset DB before each test
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /restaurants creates a restaurant")
    void whenAddRestaurant_thenRestaurantIsCreated() throws Exception {
        Restaurant restaurant = new Restaurant("Testaurant", "Porto", "http://menu.example.com");

        mvc.perform(post("/restaurants")
                        .contentType("application/json")
                        .content("{\"name\":\"Testaurant\", \"location\":\"Porto\", \"menuUrl\":\"http://menu.example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Testaurant"))
                .andExpect(jsonPath("$.location").value("Porto"))
                .andExpect(jsonPath("$.menuUrl").value("http://menu.example.com"));
    }

    @Test
    @DisplayName("GET /restaurants returns list of restaurants")
    void whenGetRestaurants_thenListIsReturned() throws Exception {
        // Create a restaurant
        Restaurant restaurant = new Restaurant("Testaurant", "Porto", "http://menu.example.com");
        restaurantRepository.save(restaurant);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name").value("Testaurant"))
                .andExpect(jsonPath("$[0].location").value("Porto"));
    }
}
