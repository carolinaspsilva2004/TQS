package tqs.hw1.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.PERSIST)    
    private Restaurant restaurant;
    
    public Meal() {
    }

    public Meal(String description, LocalDate date, Restaurant restaurant) {
        this.description = description;
        this.date = date;
        this.restaurant = restaurant;
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

    public Restaurant getRestaurant() {
        return restaurant;
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

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", restaurant=" + restaurant +
                '}';
    }
}
