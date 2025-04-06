package tqs.hw1.controllerTests;

import tqs.hw1.controller.RestaurantController;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.service.MealService;
import tqs.hw1.service.RestaurantService;
import tqs.hw1.service.WeatherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private MealService mealService;

    @MockBean
    private WeatherService weatherService;

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    @DisplayName("GET /restaurants returns all restaurants")
    void whenGetAllRestaurants_thenReturnsAllRestaurants() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Restaurant A"));
        restaurants.add(new Restaurant("Restaurant B"));

        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        mockMvc.perform(get("/restaurants").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Restaurant A")))
                .andExpect(jsonPath("$[1].name", is("Restaurant B")));

        verify(restaurantService, times(1)).getAllRestaurants();
    }

    @Test
    @DisplayName("GET /restaurants/{id} returns a restaurant by ID")
    void whenGetRestaurantById_thenReturnsRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant("Restaurant A");
        restaurant.setId(1L);

        when(restaurantService.getRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        mockMvc.perform(get("/restaurants/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Restaurant A")));

        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    @DisplayName("GET /restaurants/{id} returns 404 when restaurant not found")
    void whenGetRestaurantById_thenReturnsNotFound() throws Exception {
        when(restaurantService.getRestaurantById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/restaurants/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).getRestaurantById(999L);
    }

    @Test
    @DisplayName("POST /restaurants/add creates a new restaurant")
    void whenPostRestaurant_thenCreatesRestaurant() throws Exception {
        String restaurantJson = "{\"name\": \"Restaurant C\"}";
        Restaurant restaurant = new Restaurant("Restaurant C");

        when(restaurantService.saveRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        mockMvc.perform(post("/restaurants/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Restaurant C")));

        verify(restaurantService, times(1)).saveRestaurant(any(Restaurant.class));
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} removes a restaurant")
    void whenDeleteRestaurant_thenRemovesRestaurant() throws Exception {
        Long restaurantId = 1L;
        when(restaurantService.existsById(restaurantId)).thenReturn(true);
        doNothing().when(restaurantService).deleteRestaurant(restaurantId);

        mockMvc.perform(delete("/restaurants/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Restaurante removido com sucesso.")));

        verify(restaurantService, times(1)).deleteRestaurant(restaurantId);
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} returns error when restaurant not found")
    void whenDeleteNonExistentRestaurant_thenReturnsNotFound() throws Exception {
        Long restaurantId = 999L;
        when(restaurantService.existsById(restaurantId)).thenReturn(false);

        mockMvc.perform(delete("/restaurants/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).existsById(restaurantId);
    }

    @Test
    @DisplayName("GET /restaurants/{id}/meals returns meals for a restaurant")
    void whenGetMeals_thenReturnsMeals() throws Exception {
        Long restaurantId = 1L;
        List<Meal> meals = new ArrayList<>();
        
        // Refeição recente (deve ser retornada)
        Meal meal = new Meal("Meal A", LocalDate.parse("2025-04-09"), new Restaurant("Restaurant A"));
        meals.add(meal);

        // Refeição antiga (deve ser filtrada)
        Meal oldMeal = new Meal("Meal B", LocalDate.parse("2023-04-10"), new Restaurant("Restaurant A"));
        meals.add(oldMeal);
        
        when(restaurantService.existsById(restaurantId)).thenReturn(true);
        when(mealService.getMealsByRestaurantId(restaurantId)).thenReturn(meals);
        
        mockMvc.perform(get("/restaurants/{id}/meals", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))  // Espera-se apenas "Meal A", pois é mais recente
                .andExpect(jsonPath("$[0].description", is("Meal A")));  // Verifica se a descrição da refeição é "Meal A"
        
        verify(restaurantService, times(1)).existsById(restaurantId);
        verify(mealService, times(1)).getMealsByRestaurantId(restaurantId);
    }

    
    @Test
    @DisplayName("GET /restaurants/{id}/meals returns 404 when restaurant not found")
    void whenGetMeals_thenReturnsNotFound() throws Exception {
        Long restaurantId = 999L;

        when(restaurantService.existsById(restaurantId)).thenReturn(false);

        mockMvc.perform(get("/restaurants/{id}/meals", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).existsById(restaurantId);
    }
}
