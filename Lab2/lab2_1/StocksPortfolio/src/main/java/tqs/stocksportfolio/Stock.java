package tqs.stocksportfolio;

import jakarta.annotation.Generated;
import lombok.Data;

@Data  // Gera automaticamente getters, setters, equals, hashCode e toString
@Generated("lombok")
public class Stock {
    
    
    private String label;
    private int quantity;

    public Stock(String label, int quantity) {
        this.label = label;
        this.quantity = quantity;
    }

}
