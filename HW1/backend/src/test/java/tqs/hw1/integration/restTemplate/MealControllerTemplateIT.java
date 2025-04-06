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

import java.time.LocalDate;
import java.util.Objects;
import org.flywaydb.core.Flyway;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class MealControllerTemplateIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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
    static void setupContainer() {
        container.start();
        Flyway flyway = Flyway.configure()
            .dataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword())
            .locations("classpath:db/migration")
            .load();
    flyway.migrate();
    }

    @AfterEach
    void cleanDb() {
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/meals";
    }

    @Test
    @DisplayName("POST /meals cria uma refeição com sucesso")
    void testCreateMeal() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant("Test Resto"));
    
        Meal meal = new Meal("Pizza", LocalDate.of(2025, 4, 10), restaurant);
    
        ResponseEntity<Meal> response = restTemplate.postForEntity(baseUrl(), meal, Meal.class);
    
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo("Pizza");
        assertThat(response.getBody().getRestaurant()).isNotNull();
        assertThat(response.getBody().getRestaurant().getName()).isEqualTo("Test Resto");
    }
    

    @Test
    @DisplayName("GET /meals retorna todas as refeições")
    void testGetAllMeals() {
        Restaurant restaurant = new Restaurant("Tokyo Sushi");
        mealRepository.save(new Meal("Sushi", LocalDate.of(2025, 4, 6), restaurant));

        ResponseEntity<Meal[]> response = restTemplate.getForEntity(baseUrl(), Meal[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("GET /meals/{id} retorna uma refeição específica")
    void testGetMealById() {
        Restaurant restaurant = new Restaurant("Burger King");
        Meal meal = mealRepository.save(new Meal("Burger", LocalDate.of(2025, 4, 7), restaurant));

        ResponseEntity<Meal> response = restTemplate.getForEntity(baseUrl() + "/" + meal.getId(), Meal.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo("Burger");
    }

    @Test
    @DisplayName("GET /meals/restaurant/{restaurantId} retorna refeições por restaurante")
    void testGetMealsByRestaurantId() {
        Restaurant restaurant = new Restaurant("Casa do Frango");
        mealRepository.save(new Meal("Frango assado", LocalDate.of(2025, 4, 5), restaurant));

        ResponseEntity<Meal[]> response = restTemplate.getForEntity(baseUrl() + "/restaurant/" + restaurant.getId(), Meal[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody())[0].getDescription()).isEqualTo("Frango assado");
    }

    @Test
    @DisplayName("GET /meals/date/{date} retorna refeições por data")
    void testGetMealsByDate() {
        LocalDate date = LocalDate.of(2025, 4, 5);
        Restaurant restaurant = new Restaurant("Marisqueira");
        mealRepository.save(new Meal("Bacalhau", date, restaurant));

        ResponseEntity<Meal[]> response = restTemplate.getForEntity(baseUrl() + "/date/" + date, Meal[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody())[0].getDescription()).isEqualTo("Bacalhau");
    }

    @Test
    @DisplayName("DELETE /meals/{id} apaga a refeição se existir")
    void testDeleteMeal() {
        Restaurant restaurant = new Restaurant("Brasileiro");
        Meal meal = mealRepository.save(new Meal("Feijoada", LocalDate.of(2025, 4, 8), restaurant));

        ResponseEntity<Void> response = restTemplate.exchange(baseUrl() + "/" + meal.getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("DELETE /meals/{id} retorna 404 se não encontrar")
    void testDeleteMealNotFound() {
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl() + "/9999", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
