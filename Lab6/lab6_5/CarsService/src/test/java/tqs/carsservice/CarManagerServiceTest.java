package tqs.carsservice;

import tqs.carsservice.model.Car;
import tqs.carsservice.service.CarManagerService;
import tqs.carsservice.controller.CarController;
import tqs.carsservice.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarManagerServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindSuitableReplacementCar() {
        Car originalCar = new Car(1L, "Toyota", "Corolla", "Sedan", "Gasoline");
        Car replacementCar = new Car(2L, "Honda", "Civic", "Sedan", "Gasoline");

        when(carRepository.findById(1L)).thenReturn(Optional.of(originalCar));
        when(carRepository.findBySegmentAndEngineType("Sedan", "Gasoline"))
                .thenReturn(new ArrayList<>(Arrays.asList(originalCar, replacementCar))); // Lista mutável

        Optional<Car> foundCar = carManagerService.findReplacementCar(1L);

        assertTrue(foundCar.isPresent());
        assertEquals("Honda", foundCar.get().getMaker());
        verify(carRepository, times(1)).findById(1L);
        verify(carRepository, times(1)).findBySegmentAndEngineType("Sedan", "Gasoline");
    }

    @Test
    void shouldReturnEmptyIfNoReplacementFound() {
        Car originalCar = new Car(2L, "Ford", "Focus", "Hatchback", "Diesel");

        when(carRepository.findById(2L)).thenReturn(Optional.of(originalCar));
        when(carRepository.findBySegmentAndEngineType("Hatchback", "Diesel"))
                .thenReturn(new ArrayList<>(List.of(originalCar))); // Lista mutável

        Optional<Car> foundCar = carManagerService.findReplacementCar(2L);

        assertFalse(foundCar.isPresent());
        verify(carRepository, times(1)).findById(2L);
        verify(carRepository, times(1)).findBySegmentAndEngineType("Hatchback", "Diesel");
    }

    @Test
    void shouldReturnEmptyIfCarDoesNotExist() {
        when(carRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Car> foundCar = carManagerService.findReplacementCar(99L);

        assertFalse(foundCar.isPresent());
        verify(carRepository, times(1)).findById(99L);
        verify(carRepository, never()).findBySegmentAndEngineType(anyString(), anyString());
    }
}
