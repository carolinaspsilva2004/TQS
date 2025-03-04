package tqs.carsservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

import tqs.carsservice.model.Car;
import tqs.carsservice.repository.CarRepository;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CarRestControllerIntegrationTest {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Toyota", "Corolla", "Sedan", "Gasoline");
        restTemplate.postForEntity("/cars", car, Car.class);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Toyota");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("Toyota", "Corolla", "Sedan", "Gasoline");
        createTestCar("Honda", "Civic", "Sedan", "Gasoline");

        ResponseEntity<List<Car>> response = restTemplate.exchange(
                "/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("Toyota", "Honda");
    }

    @Test
    void givenCarId_whenGetCarById_thenReturnCar() {
        Car car = createTestCar("Ford", "Focus", "Hatchback", "Diesel");
        ResponseEntity<Car> response = restTemplate.getForEntity("/cars/" + car.getCarId(), Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMaker()).isEqualTo("Ford");
    }

    private Car createTestCar(String maker, String model, String segment, String engineType) {
        Car car = new Car(maker, model, segment, engineType);
        return carRepository.saveAndFlush(car);
    }
}
