package tqs.hw1.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.hw1.controller.RestaurantController;
import tqs.hw1.model.Restaurant;
import tqs.hw1.service.RestaurantService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setup() {
        restaurant1 = new Restaurant("Restaurant A");
        restaurant1.setId(1L);
        restaurant2 = new Restaurant("Restaurant B");
        restaurant2.setId(2L);
    }

    @Test
    @DisplayName("GET /restaurants retorna a lista de restaurantes")
    void whenGetAllRestaurants_thenReturnRestaurantsList() throws Exception {
        when(restaurantService.getAllRestaurants()).thenReturn(List.of(restaurant1, restaurant2));

        mvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Restaurant A")))
                .andExpect(jsonPath("$[1].name", is("Restaurant B")));
    }

    @Test
    @DisplayName("GET /restaurants retorna lista vazia quando não há restaurantes")
    void whenGetAllRestaurants_thenReturnEmptyList() throws Exception {
        when(restaurantService.getAllRestaurants()).thenReturn(List.of());

        mvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /restaurants/{id} retorna erro quando o restaurante não é encontrado")
    void whenGetRestaurantById_thenReturnError() throws Exception {
        when(restaurantService.getRestaurantById(999L)).thenReturn(Optional.empty());

        mvc.perform(get("/restaurants/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /restaurants/add adiciona um restaurante com sucesso")
    void whenPostRestaurant_thenCreateRestaurant() throws Exception {
        Restaurant novo = new Restaurant("Novo Restaurante");
        novo.setId(3L);
        // Corrigindo o mock do método createRestaurant
        when(restaurantService.saveRestaurant(any(Restaurant.class))).thenReturn(novo);

        String restaurantJson = objectMapper.writeValueAsString(novo);

        mvc.perform(post("/restaurants/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Novo Restaurante")));
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} remove um restaurante existente")
    void whenDeleteRestaurant_thenRemoveRestaurant() throws Exception {
        when(restaurantService.existsById(1L)).thenReturn(true);
        doNothing().when(restaurantService).deleteRestaurant(1L);

        mvc.perform(delete("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} retorna erro quando o restaurante não existe")
    void whenDeleteNonExistentRestaurant_thenReturnError() throws Exception {
        when(restaurantService.existsById(999L)).thenReturn(false);

        mvc.perform(delete("/restaurants/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
