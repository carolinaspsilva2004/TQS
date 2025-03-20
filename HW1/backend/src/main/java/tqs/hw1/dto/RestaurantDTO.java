package tqs.hw1.dto;

import lombok.Data;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String externalMenuUrl;
}