// package tqs.hw1.controller.containers;

// import io.restassured.RestAssured;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.testcontainers.containers.PostgreSQLContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.springframework.test.context.DynamicPropertySource;
// import org.springframework.test.context.DynamicPropertyRegistry;

// import static org.hamcrest.Matchers.equalTo;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Testcontainers
// public class WeatherControllerTest {

//     @Container
//     public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
//             .withDatabaseName("test")
//             .withUsername("test")
//             .withPassword("test");

//     @LocalServerPort
//     private int port;

//     @BeforeAll
//     static void setupContainer() {
//         postgres.start();
//     }



//     @Test
//     void whenGetWeatherForCityAndDate_thenReturnWeather() {
//         String city = "Aveiro";
//         String date = "2024-03-25";
        
//         // Update the expected conditions based on the response data
//         String expectedCondition = "Partially cloudy";  // Use the correct expected condition from the response

//         RestAssured.given()
//                 .port(port)
//                 .get("/weather/" + city + "/" + date)
//                 .then()
//                 .statusCode(200)
//                 .body("days[0].conditions", equalTo(expectedCondition));
//     }
// }
