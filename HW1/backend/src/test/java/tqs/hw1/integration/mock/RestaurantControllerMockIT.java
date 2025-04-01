package tqs.hw1.controller.integration.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.RestaurantRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RestaurantControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setup() {
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /restaurants creates a restaurant")
    void whenAddRestaurant_thenRestaurantIsCreated() throws Exception {
        mvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Testaurant\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Testaurant"));
    }

    @Test
    @DisplayName("GET /restaurants returns list of restaurants")
    void whenGetRestaurants_thenListIsReturned() throws Exception {
        restaurantRepository.save(new Restaurant("Testaurant"));

        mvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Testaurant"));
    }

    @Test
    @DisplayName("GET /restaurants/{id} returns a restaurant by id")
    void whenGetRestaurantById_thenReturnRestaurant() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Testaurant"));

        mvc.perform(get("/restaurants/" + restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Testaurant"));
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} deletes a restaurant")
    void whenDeleteRestaurant_thenRestaurantIsDeleted() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Testaurant"));

        mvc.perform(delete("/restaurants/" + restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Restaurante removido com sucesso."));
    }

    @Test
    @DisplayName("GET /restaurants/{id}/meals returns meals for a restaurant")
    void whenGetMealsForRestaurant_thenReturnMeals() throws Exception {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Testaurant"));

        mvc.perform(get("/restaurants/" + restaurant.getId() + "/meals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
