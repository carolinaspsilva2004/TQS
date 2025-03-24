package tqs.carsservice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.carsservice.controller.CarController;
import tqs.carsservice.model.Car;
import tqs.carsservice.service.CarManagerService;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(CarController.class)
public class CarControllerRestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void whenGetAllCars_thenReturnList() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");

        Mockito.when(carManagerService.getAllCars()).thenReturn(Arrays.asList(car1, car2));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("[0].maker", equalTo("Toyota"))
                .body("[1].model", equalTo("Civic"));
    }

    @Test
    public void whenGetCarById_thenReturnCar() {
        Car car = new Car("Ford", "Fiesta");
        Mockito.when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(car));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/cars/1")
                .then()
                .statusCode(200)
                .body("maker", equalTo("Ford"))
                .body("model", equalTo("Fiesta"));
    }

    @Test
    public void whenGetCarByInvalidId_thenReturnNotFound() {
        Mockito.when(carManagerService.getCarDetails(99L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                .when()
                .get("/cars/99")
                .then()
                .statusCode(404);
    }
}
