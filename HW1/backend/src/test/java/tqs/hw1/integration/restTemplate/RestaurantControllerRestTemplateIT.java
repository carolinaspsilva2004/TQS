package tqs.hw1.integration.resttemplate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.RestaurantRepository;
import tqs.hw1.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class RestaurantControllerRestTemplateIT {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @BeforeAll
    static void setupContainer() {
        container.start();
    }

    @AfterEach
    public void resetDb() {
        restaurantRepository.deleteAll();
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/restaurants";
    }

    @Test
    @DisplayName("GET /restaurants retorna lista vazia quando não há restaurantes")
    void whenGetAllRestaurants_thenReturnEmptyList() {
        ResponseEntity<Restaurant[]> response = restTemplate.getForEntity(baseUrl(), Restaurant[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("POST /restaurants cria um restaurante com sucesso")
    void whenCreateRestaurant_thenReturnCreatedRestaurant() {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        ResponseEntity<Restaurant> response = restTemplate.postForEntity(baseUrl() + "/add", restaurant, Restaurant.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Restaurant");
    }

    @Test
    @DisplayName("GET /restaurants/{id} retorna erro quando restaurante não existe")
    void whenGetRestaurantById_thenReturnError() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} remove um restaurante com sucesso")
    void whenDeleteRestaurant_thenRemoveSuccessfully() {
        Restaurant restaurant = new Restaurant("To Be Deleted");
        Restaurant saved = restaurantRepository.save(restaurant);

        restTemplate.delete(baseUrl() + "/" + saved.getId());

        assertThat(restaurantRepository.findById(saved.getId())).isEmpty();
    }
}
