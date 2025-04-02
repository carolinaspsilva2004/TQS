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
import tqs.hw1.controller.MealController;  

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
public class MealControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeAll
    static void setupContainer() {
        container.start();
    }

    @AfterEach
    public void resetDb() {
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @Test
    @DisplayName("GET /meals retorna a lista de refeições")
    void whenGetAllMeals_thenReturnMealsList() throws Exception {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        restaurantRepository.save(restaurant);
        
        Meal meal1 = new Meal("Meal 1", LocalDate.now(), restaurant);
        Meal meal2 = new Meal("Meal 2", LocalDate.now(), restaurant);
        mealRepository.saveAll(List.of(meal1, meal2));

        mvc.perform(get("/meals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    @DisplayName("GET /meals retorna lista vazia quando não há refeições")
    void whenGetAllMeals_thenReturnEmptyList() throws Exception {
        mealRepository.deleteAll();

        mvc.perform(get("/meals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /meals/{id} retorna erro quando a refeição não é encontrada")
    void whenGetMealById_thenReturnError() throws Exception {
        Long nonExistentId = 999L;

        mvc.perform(get("/meals/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /meals cria uma refeição com sucesso")
    void whenCreateMeal_thenReturnCreatedMeal() throws Exception {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        restaurantRepository.save(restaurant);

        Meal meal = new Meal("Test Meal", LocalDate.now(), restaurant);

        mvc.perform(post("/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"description\": \"Test Meal\", \"date\": \"" + LocalDate.now() + "\", \"restaurant\": { \"id\": " + restaurant.getId() + " } }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("Test Meal")))
                .andExpect(jsonPath("$.date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.restaurant.id", is(restaurant.getId().intValue())));
    }
}
