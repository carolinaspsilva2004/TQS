package tqs.hw1.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private String code;
    private Long mealId;
    private boolean used;
    private LocalDateTime reservationDate;

    public ReservationDTO() {
    }

    public ReservationDTO(String code, Long mealId, boolean used, LocalDateTime reservationDate) {
        this.code = code;
        this.mealId = mealId;
        this.used = used;
        this.reservationDate = reservationDate;
    }

    public String getCode() {
        return code;
    }

    public Long getMealId() {
        return mealId;
    }


    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "code='" + code + '\'' +
                ", mealId=" + mealId +
                ", used=" + used +
                ", reservationDate=" + reservationDate +
                '}';
    }
}