package tqs.carsservice.service;

import tqs.carsservice.model.Car;
import tqs.carsservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    @Autowired
    private CarRepository carRepository;

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long id) {
        return carRepository.findById(id);
    }

    public Optional<Car> findReplacementCar(Long carId) {
        Optional<Car> originalCar = carRepository.findById(carId);
        
        if (originalCar.isPresent()) {
            Car car = originalCar.get();
            List<Car> suitableCars = carRepository.findBySegmentAndEngineType(car.getSegment(), car.getEngineType());

            // Removendo o próprio carro da lista de substitutos
            suitableCars.removeIf(c -> c.getCarId().equals(carId));

            return suitableCars.stream().findFirst(); // Retorna o primeiro disponível
        }
        return Optional.empty();
    }
}
