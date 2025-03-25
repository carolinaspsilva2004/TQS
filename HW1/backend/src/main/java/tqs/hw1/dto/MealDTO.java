package tqs.hw1.dto;

import java.time.LocalDate;

public class MealDTO {
    private Long id;
    private String description;
    private LocalDate date;
    private Long restaurantId;
    private String weather;

    public MealDTO() {
    }

    public MealDTO(Long id, String description, LocalDate date, Long restaurantId, String weather) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.restaurantId = restaurantId;
        this.weather = weather;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getWeather() {
        return weather;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "MealDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", restaurantId=" + restaurantId +
                ", weather='" + weather + '\'' +
                '}';
    }
}