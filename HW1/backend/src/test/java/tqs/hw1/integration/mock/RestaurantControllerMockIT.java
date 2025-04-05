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
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.RestaurantRepository;

import java.time.LocalDate;
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

    @Autowired
    private MealRepository mealRepository;

    @BeforeAll
    static void setupContainer() {
        container.start();
    }

    @AfterEach
    public void resetDb() {
        restaurantRepository.deleteAll();
        mealRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @Test
    @DisplayName("GET /restaurants/{id}/meals retorna as refeições de um restaurante")
    void whenGetMealsByRestaurant_thenReturnMeals() throws Exception {
        Restaurant restaurant = new Restaurant("Restaurant A");
        restaurant = restaurantRepository.save(restaurant);

        Meal meal1 = new Meal("Meal 1", LocalDate.parse("2025-04-05"), restaurant);
        Meal meal2 = new Meal("Meal 2", LocalDate.parse("2025-04-06"), restaurant);
        mealRepository.saveAll(List.of(meal1, meal2));

        mvc.perform(get("/restaurants/{id}/meals", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que há 2 refeições
                .andExpect(jsonPath("$[0].name", is("Meal 1"))) // Verifica o nome da refeição
                .andExpect(jsonPath("$[1].name", is("Meal 2"))); // Verifica o nome da refeição
    }

    @Test
    @DisplayName("GET /restaurants/{id}/meals retorna lista vazia quando não há refeições")
    void whenGetMealsByRestaurant_thenReturnEmptyList() throws Exception {
        Restaurant restaurant = new Restaurant("Restaurant A");
        restaurant = restaurantRepository.save(restaurant);

        mvc.perform(get("/restaurants/{id}/meals", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0))); // Espera uma lista vazia
    }

    @Test
    @DisplayName("GET /restaurants/{id}/meals retorna erro quando o restaurante não é encontrado")
    void whenGetMealsByNonExistentRestaurant_thenReturnError() throws Exception {
        Long nonExistentId = 999L;

        mvc.perform(get("/restaurants/{id}/meals", nonExistentId)
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
