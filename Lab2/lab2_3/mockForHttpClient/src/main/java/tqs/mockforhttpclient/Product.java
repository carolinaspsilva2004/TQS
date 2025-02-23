package tqs.mockforhttpclient;

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
    Rating rating;  // Adicionando o campo Rating

    // Classe interna para o campo Rating
    @Data
    public static class Rating {
        double rate;
        int count;
    }
}

