package tqs.carsservice.repository;

import tqs.carsservice.model.Car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
        List<Car> findBySegmentAndEngineType(String segment, String engineType);
}
