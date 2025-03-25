package tqs.hw1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDateTime reservationDate;
    private boolean used;

    @ManyToOne
    private Meal meal;

    public Reservation() {
    }

    public Reservation(String code, LocalDateTime reservationDate, boolean used, Meal meal) {
        this.code = code;
        this.reservationDate = reservationDate;
        this.used = used;
        this.meal = meal;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public boolean isUsed() {
        return used;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", reservationDate=" + reservationDate +
                ", used=" + used +
                ", meal=" + meal +
                '}';
    }
}
