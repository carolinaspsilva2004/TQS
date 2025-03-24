package tqs.carsservice;

import tqs.carsservice.model.Car;
import tqs.carsservice.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Testa somente a camada de persistência
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void shouldSaveAndRetrieveCar() {
        Car car = new Car("Toyota", "Corolla");
        carRepository.save(car);

        List<Car> cars = carRepository.findAll();
        assertEquals(1, cars.size());
        assertEquals("Toyota", cars.get(0).getMaker());
    }

    @Test
    void shouldFindCarById() {
        Car car = new Car("Honda", "Civic");
        car = carRepository.save(car); // Salva e recebe um ID gerado

        Optional<Car> foundCar = carRepository.findById(car.getCarId());
        assertTrue(foundCar.isPresent());
        assertEquals("Honda", foundCar.get().getMaker());
    }

    @Test
    void shouldReturnEmptyForNonExistingCar() {
        Optional<Car> car = carRepository.findById(999L);
        assertFalse(car.isPresent());
    }

    @Test
    void shouldSaveMultipleCars() {
        Car car1 = new Car("Ford", "Focus");
        Car car2 = new Car("Chevrolet", "Malibu");

        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> cars = carRepository.findAll();
        assertEquals(2, cars.size());
    }
}
