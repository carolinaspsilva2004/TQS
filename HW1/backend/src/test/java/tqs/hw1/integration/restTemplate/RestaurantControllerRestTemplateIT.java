package tqs.hw1.integration.restTemplate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.hw1.model.Meal;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.repository.RestaurantRepository;
import org.flywaydb.core.Flyway;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class RestaurantControllerRestTemplateIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MealRepository mealRepository;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @BeforeAll
    static void setupContainer() {
        container.start();
        Flyway flyway = Flyway.configure()
            .dataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword())
            .locations("classpath:db/migration")
            .load();
        flyway.migrate();
    }

    @AfterEach
    public void resetDb() {
        restaurantRepository.deleteAll();
        mealRepository.deleteAll();
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/restaurants";
    }

    @Test
    @DisplayName("GET /restaurants retorna lista vazia quando não há restaurantes")
    void whenGetAllRestaurants_thenReturnEmptyList() {
        ResponseEntity<Restaurant[]> response = testRestTemplate.getForEntity(baseUrl(), Restaurant[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("GET /restaurants retorna a lista de restaurantes")
    void whenGetAllRestaurants_thenReturnRestaurantsList() {
        Restaurant restaurant1 = new Restaurant("Restaurant A");
        Restaurant restaurant2 = new Restaurant("Restaurant B");
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2));

        ResponseEntity<Restaurant[]> response = testRestTemplate.getForEntity(baseUrl(), Restaurant[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().hasSize(2);
    }

    @Test
    @DisplayName("POST /restaurants cria um restaurante com sucesso")
    void whenCreateRestaurant_thenReturnCreatedRestaurant() {
        Restaurant restaurant = new Restaurant("Test Restaurant");
        ResponseEntity<Restaurant> response = testRestTemplate.postForEntity(baseUrl() + "/add", restaurant, Restaurant.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Restaurant");
    }

    @Test
    @DisplayName("GET /restaurants/{id} retorna erro quando restaurante não existe")
    void whenGetRestaurantById_thenReturnError() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl() + "/999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("GET /restaurants/{id} retorna restaurante existente")
    void whenGetRestaurantById_thenReturnRestaurant() {
        Restaurant restaurant = new Restaurant("Restaurant A");
        restaurant = restaurantRepository.save(restaurant);

        ResponseEntity<Restaurant> response = testRestTemplate.getForEntity(baseUrl() + "/{id}", Restaurant.class, restaurant.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Restaurant A");
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} remove um restaurante com sucesso")
    void whenDeleteRestaurant_thenRemoveSuccessfully() {
        Restaurant restaurant = new Restaurant("To Be Deleted");
        Restaurant saved = restaurantRepository.save(restaurant);

        testRestTemplate.delete(baseUrl() + "/" + saved.getId());

        assertThat(restaurantRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} retorna erro quando restaurante não existe")
    void whenDeleteRestaurant_thenReturnError() {
        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl() + "/999", HttpMethod.DELETE, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("GET /restaurants/{id}/meals retorna refeições de um restaurante")
    void whenGetMealsByRestaurant_thenReturnMeals() {
        // Criação de restaurante
        Restaurant restaurant = new Restaurant("Restaurant A");
        restaurant = restaurantRepository.save(restaurant);

        // Criação de refeições associadas ao restaurante
        Meal meal1 = new Meal("Meal 1", LocalDate.parse("2025-04-05"), restaurant);
        Meal meal2 = new Meal("Meal 2", LocalDate.parse("2025-04-06"), restaurant);
        mealRepository.saveAll(List.of(meal1, meal2));

        // Requisição GET para o endpoint de refeições
        ResponseEntity<Meal[]> response = testRestTemplate.getForEntity(baseUrl() + "/{id}/meals", Meal[].class, restaurant.getId());

        // Verificação do status e do conteúdo da resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().hasSize(2);
        assertThat(response.getBody()[0].getName()).isEqualTo("Meal 1");
        assertThat(response.getBody()[1].getName()).isEqualTo("Meal 2");
    }

    @Test
    @DisplayName("GET /restaurants/{id}/meals retorna lista vazia quando não há refeições")
    void whenGetMealsByRestaurant_thenReturnEmptyList() {
        // Criação de restaurante sem refeições associadas
        Restaurant restaurant = new Restaurant("Restaurant A");
        restaurant = restaurantRepository.save(restaurant);

        // Requisição GET para o endpoint de refeições
        ResponseEntity<Meal[]> response = testRestTemplate.getForEntity(baseUrl() + "/{id}/meals", Meal[].class, restaurant.getId());

        // Verificação do status e do conteúdo da resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("GET /restaurants/{id}/meals retorna erro quando o restaurante não existe")
    void whenGetMealsByNonExistentRestaurant_thenReturnError() {
        Long nonExistentId = 999L;

        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl() + "/{id}/meals", String.class, nonExistentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
