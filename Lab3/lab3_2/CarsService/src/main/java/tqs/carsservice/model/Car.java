package tqs.carsservice.model;

import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    private String maker;
    private String model;
    private String segment;  // Ex: "SUV", "Sedan", "Hatchback"
    private String engineType; // Ex: "Gasoline", "Electric", "Diesel"

    public Car() {}

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car(String maker, String model, String segment, String engineType) {
        this.maker = maker;
        this.model = model;
        this.segment = segment;
        this.engineType = engineType;
    }

    public Car(Long carId, String maker, String model, String segment, String engineType) {
        this.carId = carId;
        this.maker = maker;
        this.model = model;
        this.segment = segment;
        this.engineType = engineType;
    }

    // Getters e Setters
    public Long getCarId() { return carId; }
    public String getMaker() { return maker; }
    public String getSegment() { return segment; }
    public String getEngineType() { return engineType; }


    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                ", segment='" + segment + '\'' +
                ", engineType='" + engineType + '\'' +
                '}';
    }
}
