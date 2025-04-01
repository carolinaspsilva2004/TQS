package tqs.hw1.integration.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.RestaurantRepository;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
public class RestaurantControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeAll
    static void setupContainer() {
        container.start();
    }

    @AfterEach
    public void resetDb() {
        restaurantRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @Test
    @DisplayName("GET /restaurants retorna a lista de restaurantes")
    void whenGetAllRestaurants_thenReturnRestaurantsList() throws Exception {
        Restaurant restaurant1 = new Restaurant("Restaurant A");
        Restaurant restaurant2 = new Restaurant("Restaurant B");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2));

        mvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    @DisplayName("GET /restaurants retorna lista vazia quando não há restaurantes")
    void whenGetAllRestaurants_thenReturnEmptyList() throws Exception {
        restaurantRepository.deleteAll();

        mvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /restaurants/{id} retorna erro quando o restaurante não é encontrado")
    void whenGetRestaurantById_thenReturnError() throws Exception {
        Long nonExistentId = 999L;

        mvc.perform(get("/restaurants/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /restaurants/add adiciona um restaurante com sucesso")
    void whenPostRestaurant_thenCreateRestaurant() throws Exception {
        String restaurantJson = "{\"name\": \"Novo Restaurante\"}";

        mvc.perform(post("/restaurants/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Novo Restaurante")));
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} remove um restaurante existente")
    void whenDeleteRestaurant_thenRemoveRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant("Restaurante para Remover");
        restaurant = restaurantRepository.save(restaurant);

        mvc.perform(delete("/restaurants/{id}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} retorna erro quando o restaurante não existe")
    void whenDeleteNonExistentRestaurant_thenReturnError() throws Exception {
        Long nonExistentId = 999L;

        mvc.perform(delete("/restaurants/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
