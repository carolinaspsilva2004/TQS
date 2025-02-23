
package tqs.products;

import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;

// ProductFinderService class
public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products/";
    private final ISimpleHttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(int id) {
        try {
            String jsonResponse = httpClient.doHttpGet(API_PRODUCTS + id);
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                return Optional.empty();
            }
            Product product = objectMapper.readValue(jsonResponse, Product.class);
            return Optional.of(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}