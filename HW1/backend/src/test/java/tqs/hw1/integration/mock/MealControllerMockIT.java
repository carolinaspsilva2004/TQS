package tqs.hw1.integration.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

import java.time.LocalDate;

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
    private ObjectMapper objectMapper;

    @AfterEach
    public void cleanUp() {
        mealRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");


    @Test
    @DisplayName("POST /meals creates a meal successfully")
    void testCreateMeal() throws Exception {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        Meal meal = new Meal("Pizza", LocalDate.of(2025, 4, 5), restaurant);

        mvc.perform(post("/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("Pizza")));
    }

    @Test
    @DisplayName("GET /meals returns all meals")
    void testGetAllMeals() throws Exception {
        Meal meal = new Meal("Sushi", LocalDate.of(2025, 4, 6), new Restaurant("Tokyo Sushi"));
        mealRepository.save(meal);

        mvc.perform(get("/meals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("GET /meals/{id} returns specific meal")
    void testGetMealById() throws Exception {
        Meal meal = new Meal("Burger", LocalDate.of(2025, 4, 7), new Restaurant("Burger King"));
        meal = mealRepository.save(meal);

        mvc.perform(get("/meals/" + meal.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Burger")));
    }

    @Test
    @DisplayName("GET /meals/restaurant/{restaurantId} returns meals by restaurant ID")
    void testGetMealsByRestaurantId() throws Exception {
        Restaurant restaurant = new Restaurant("Casa do Frango");
        Meal meal = new Meal("Frango assado", LocalDate.of(2025, 4, 5), restaurant);
        mealRepository.save(meal);

        mvc.perform(get("/meals/restaurant/" + meal.getRestaurant().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Frango assado")));
    }

    @Test
    @DisplayName("GET /meals/date/{date} returns meals by date")
    void testGetMealsByDate() throws Exception {
        LocalDate date = LocalDate.of(2025, 4, 5);
        Meal meal = new Meal("Bacalhau", date, new Restaurant("Marisqueira"));
        mealRepository.save(meal);

        mvc.perform(get("/meals/date/" + date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Bacalhau")));
    }

    @Test
    @DisplayName("DELETE /meals/{id} deletes a meal if exists")
    void testDeleteMeal() throws Exception {
        Meal meal = new Meal("Feijoada", LocalDate.of(2025, 4, 8), new Restaurant("Brasileiro"));
        meal = mealRepository.save(meal);

        mvc.perform(delete("/meals/" + meal.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /meals/{id} returns 404 if meal not found")
    void testDeleteMealNotFound() throws Exception {
        mvc.perform(delete("/meals/999"))
                .andExpect(status().isNotFound());
    }
}
