package tqs.hw1.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private String code;
    private Long mealId;
    private boolean used;
    private LocalDateTime reservationDate;
}