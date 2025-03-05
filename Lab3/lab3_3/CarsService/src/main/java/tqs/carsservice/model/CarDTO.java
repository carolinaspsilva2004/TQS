package tqs.carsservice.model;

public class CarDTO {

    private Long carId;
    private String maker;
    private String model;
    private String segment;  // Ex: "SUV", "Sedan", "Hatchback"
    private String engineType; // Ex: "Gasoline", "Electric", "Diesel"

    // Construtores
    public CarDTO() {}

    public CarDTO(String maker, String model, String segment, String engineType) {
        this.maker = maker;
        this.model = model;
        this.segment = segment;
        this.engineType = engineType;
    }

    public CarDTO(Long carId, String maker, String model, String segment, String engineType) {
        this.carId = carId;
        this.maker = maker;
        this.model = model;
        this.segment = segment;
        this.engineType = engineType;
    }

    // Getters e Setters
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "carId=" + carId +
                ", maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                ", segment='" + segment + '\'' +
                ", engineType='" + engineType + '\'' +
                '}';
    }
}
