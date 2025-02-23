package tqs.products;

import lombok.Data;

// Product class
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
public class Product {
    int id;
    String title;
    double price;
    String description;
    String category;
    String image;


}
