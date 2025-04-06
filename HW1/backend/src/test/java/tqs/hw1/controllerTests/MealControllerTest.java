package tqs.hw1.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.controller.MealController;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.service.MealService;
import tqs.hw1.service.RestaurantService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealController.class)
public class MealControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MealService mealService;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
@DisplayName("POST /meals creates a meal successfully")
void testCreateMeal() throws Exception {
    // Criação do Restaurant com ID válido
    Restaurant restaurant = new Restaurant("Restaurante");
    restaurant.setId(1L);

    // Configuração do Meal com o ID do Restaurant
    Meal meal = new Meal("Pizza", LocalDate.of(2025, 4, 10), restaurant);
    meal.setId(1L);

    // Mocking do serviço de restaurant e meal
    Mockito.when(restaurantService.getRestaurantById(eq(1L))).thenReturn(Optional.of(restaurant));
    Mockito.when(mealService.saveMeal(any(Meal.class))).thenReturn(meal);

    // Perform the POST request to create a meal
    mvc.perform(post("/meals")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(meal)))
            .andExpect(status().isOk()) // Espera status 201 (Created)
            .andExpect(jsonPath("$.id", is(1))) // Verifica o ID
            .andExpect(jsonPath("$.description", is("Pizza"))); // Verifica a descrição
}

    

    @Test
    @DisplayName("GET /meals returns all meals")
    void testGetAllMeals() throws Exception {
        Meal meal = new Meal("Sushi", LocalDate.of(2025, 4, 6), new Restaurant("Tokyo Sushi"));
        meal.setId(1L);

        Mockito.when(mealService.getAllMeals()).thenReturn(Collections.singletonList(meal));

        mvc.perform(get("/meals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Sushi")));
    }

    @Test
    @DisplayName("GET /meals/{id} returns specific meal")
    void testGetMealById() throws Exception {
        Meal meal = new Meal("Burger", LocalDate.of(2025, 4, 7), new Restaurant("Burger King"));
        meal.setId(1L);

        Mockito.when(mealService.getMealById(1L)).thenReturn(Optional.of(meal));

        mvc.perform(get("/meals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Burger")));
    }

    @Test
    @DisplayName("GET /meals/restaurant/{restaurantId} returns meals by restaurant ID")
    void testGetMealsByRestaurantId() throws Exception {
        Meal meal = new Meal("Frango assado", LocalDate.of(2025, 4, 5), new Restaurant("Casa do Frango"));
        meal.setId(1L);

        Mockito.when(mealService.getMealsByRestaurantId(1L)).thenReturn(Collections.singletonList(meal));

        mvc.perform(get("/meals/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Frango assado")));
    }

    @Test
    @DisplayName("GET /meals/date/{date} returns meals by date")
    void testGetMealsByDate() throws Exception {
        LocalDate date = LocalDate.of(2025, 4, 5);
        Meal meal = new Meal("Bacalhau", date, new Restaurant("Marisqueira"));
        meal.setId(1L);

        Mockito.when(mealService.getMealsByDate(date)).thenReturn(Collections.singletonList(meal));

        mvc.perform(get("/meals/date/" + date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Bacalhau")));
    }

    @Test
    @DisplayName("DELETE /meals/{id} deletes a meal if exists")
    void testDeleteMeal() throws Exception {
        Mockito.when(mealService.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(mealService).deleteMeal(1L);

        mvc.perform(delete("/meals/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /meals/{id} returns 404 if meal not found")
    void testDeleteMealNotFound() throws Exception {
        Mockito.when(mealService.existsById(999L)).thenReturn(false);

        mvc.perform(delete("/meals/999"))
                .andExpect(status().isNotFound());
    }
}
