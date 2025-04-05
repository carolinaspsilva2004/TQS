// package tqs.hw1.controller.integration.restTemplate;

// import io.restassured.RestAssured;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.AfterEach;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.testcontainers.containers.PostgreSQLContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.springframework.test.context.DynamicPropertySource;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import tqs.hw1.model.Meal;
// import tqs.hw1.repository.MealRepository;

// import static org.hamcrest.Matchers.*;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Testcontainers
// public class MealControllerTemplateIT {

//     @Container
//     public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
//             .withDatabaseName("test")
//             .withUsername("test")
//             .withPassword("test");

//     @LocalServerPort
//     private int port;

//     @Autowired
//     private MealRepository mealRepository;

//     @BeforeAll
//     static void setupContainer() {
//         postgres.start();
//     }

//     @BeforeEach
//     void setup() {
//         mealRepository.deleteAll();
//     }

//     @AfterEach
//     void tearDown() {
//         mealRepository.deleteAll();
//     }
//     @Test
//     void whenGetAllMeals_thenReturnMealsList() {
//         Meal meal1 = new Meal();
//         Meal meal2 = new Meal();
//         mealRepository.save(meal1);
//         mealRepository.save(meal2);

//         RestAssured.given()
//                 .port(port)
//                 .get("/meals")
//                 .then()
//                 .statusCode(200)
//                 .body("$", hasSize(2));
//     }

//     @Test
//     void whenGetAllMeals_thenReturnEmptyList() {
//         RestAssured.given()
//                 .port(port)
//                 .get("/meals")
//                 .then()
//                 .statusCode(200)
//                 .body("$", hasSize(0));
//     }
// }
