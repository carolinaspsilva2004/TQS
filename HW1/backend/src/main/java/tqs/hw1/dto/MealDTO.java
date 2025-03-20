package tqs.hw1.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MealDTO {
    private Long id;
    private String description;
    private LocalDate date;
    private Long restaurantId;
    private String weather;
}