package tqs.products;

import java.util.Optional;
import lombok.Data;

// Product class
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
public class Product {
    private Integer id;
    private String image;
    private String description;
    private Double price;
    private String title;
    private String category;


    // Default constructor
    public Product() {
    }

    // Getters and setters (not implemented yet)
}
