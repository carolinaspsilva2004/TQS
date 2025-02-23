
package tqs.products;

// ProductFinderService class
public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products/";
    private final ISimpleHttpClient httpClient;

    // Constructor
    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    // Method to find product details (not implemented yet)
    public Optional<Product> findProductDetails(Integer id) {
        return Optional.empty();
    }
}