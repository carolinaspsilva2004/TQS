package tqs.hw1.integration.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @BeforeAll
    static void startContainer() {
        container.start();
    }

    @BeforeEach
    public void setup() {
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @AfterEach
    public void resetDb() {
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /meals retorna a lista de refeições")
    void whenGetAllMeals_thenReturnMealsList() throws Exception {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        restaurant = restaurantRepository.saveAndFlush(restaurant);
        
        Meal meal1 = new Meal("Meal 1", LocalDate.now(), restaurant);
        Meal meal2 = new Meal("Meal 2", LocalDate.now(), restaurant);
        mealRepository.saveAll(List.of(meal1, meal2));

        mvc.perform(get("/meals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("GET /meals/{id} retorna uma refeição específica")
    void whenGetMealById_thenReturnMeal() throws Exception {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        restaurant = restaurantRepository.saveAndFlush(restaurant); 
        
        Meal meal = new Meal("Specific Meal", LocalDate.now(), restaurant);
        meal = mealRepository.save(meal);

        mvc.perform(get("/meals/{id}", meal.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Specific Meal")));
    }

    @Test
    @DisplayName("GET /meals/{id} retorna 404 para refeição inexistente")
    void whenGetNonExistingMeal_thenReturn404() throws Exception {
        mvc.perform(get("/meals/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /meals/restaurant/{restaurantId} retorna refeições de um restaurante")
    void whenGetMealsByRestaurantId_thenReturnMeals() throws Exception {
        Restaurant restaurant = new Restaurant("Restaurant Meals");
        restaurant = restaurantRepository.saveAndFlush(restaurant);

        Meal meal = new Meal("Restaurant Meal", LocalDate.now(), restaurant);
        mealRepository.save(meal);

        mvc.perform(get("/meals/restaurant/{restaurantId}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Restaurant Meal")));
    }

    @Test
    @DisplayName("GET /meals/date/{date} retorna refeições por data")
    void whenGetMealsByDate_thenReturnMeals() throws Exception {
        Restaurant restaurant = new Restaurant("Date Restaurant");
        restaurant = restaurantRepository.saveAndFlush(restaurant);

        LocalDate today = LocalDate.now();
        Meal meal = new Meal("Date Meal", today, restaurant);
        mealRepository.save(meal);

        mvc.perform(get("/meals/date/{date}", today.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Date Meal")));
    }

    @Test
    @DisplayName("POST /meals cria uma refeição com sucesso")
    void whenCreateMeal_thenCreateSuccessfully() throws Exception {
        Restaurant restaurant = new Restaurant("Post Restaurant");
        restaurant = restaurantRepository.saveAndFlush(restaurant);

        String mealJson = "{ \"description\": \"Posted Meal\", \"date\": \"" + LocalDate.now() + "\", \"restaurant\": { \"id\": " + restaurant.getId() + " } }";

        mvc.perform(post("/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mealJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Posted Meal")))
                .andExpect(jsonPath("$.restaurant.id", is(restaurant.getId().intValue())));
    }

    @Test
    @DisplayName("DELETE /meals/{id} remove uma refeição existente")
    void whenDeleteMeal_thenDeleteSuccessfully() throws Exception {
        Restaurant restaurant = new Restaurant("Delete Restaurant");
        restaurant = restaurantRepository.saveAndFlush(restaurant); 

        Meal meal = new Meal("Deletable Meal", LocalDate.now(), restaurant);
        meal = mealRepository.save(meal);

        mvc.perform(delete("/meals/{id}", meal.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /meals/{id} retorna 404 se refeição não existir")
    void whenDeleteNonExistingMeal_thenReturn404() throws Exception {
        mvc.perform(delete("/meals/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
