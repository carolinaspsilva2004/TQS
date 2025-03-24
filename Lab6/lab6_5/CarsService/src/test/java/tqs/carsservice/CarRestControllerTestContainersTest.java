package tqs.carsservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import tqs.carsservice.model.Car;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-integration.properties")
@Testcontainers
public class CarRestControllerTestContainersTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setupContainer() {
        postgres.start();
        System.out.println("Postgres Container is running at: " + postgres.getJdbcUrl());
    }


    @Test
    void whenPostCar_thenCarIsCreated() {
        Car car = new Car("Tesla", "Model S", "Sedan", "Electric");

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(car)
                .post("/cars")
                .then()
                .statusCode(200)
                .body("maker", equalTo("Tesla"))
                .body("model", equalTo("Model S"));
    }

    @Test
    void whenAddCarsAndGetAll_thenCorrectList() {
        Car car1 = new Car("BMW", "X5", "SUV", "Diesel");
        Car car2 = new Car("Audi", "A4", "Sedan", "Gasoline");

        RestAssured.given().port(port).contentType(ContentType.JSON).body(car1).post("/cars");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(car2).post("/cars");

        RestAssured.given()
                .port(port)
                .get("/cars")
                .then()
                .statusCode(200)
                .body("$", hasSize(3));
    }
}
