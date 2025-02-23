package tqs.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

class ProductFinderServiceTest {
    
    private ProductFinderService productFinderService;
    
    @Mock
    private ISimpleHttpClient httpClient;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productFinderService = new ProductFinderService(httpClient);
    }
    
    @Test
    void testFindProductDetails_ValidProduct() {
        String jsonResponse = "{\"id\":3,\"title\":\"Mens Cotton Jacket\",\"price\":55.99,\"description\":\"Great jacket.\",\"category\":\"men's clothing\",\"image\":\"image_url\"}";
        when(httpClient.doHttpGet(anyString())).thenReturn(jsonResponse);
        
        Optional<Product> product = productFinderService.findProductDetails(3);
        
        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());
    }
    
    @Test
    void testFindProductDetails_ProductNotFound() {
        when(httpClient.doHttpGet(anyString())).thenReturn(null);
        
        Optional<Product> product = productFinderService.findProductDetails(300);
        
        assertFalse(product.isPresent());
    }

    @Test
    void testFindProductDetails_ExceptionThrown() {
        // Simula uma exceção durante a chamada HTTP
        when(httpClient.doHttpGet(anyString())).thenThrow(new RuntimeException("Network error"));
        
        Optional<Product> product = productFinderService.findProductDetails(3);
        
        // Verifica que, em caso de erro, o retorno seja um Optional vazio
        assertFalse(product.isPresent());
    }

    @Test
    void testFindProductDetails_EmptyResponse() {
        // Simula uma resposta vazia ou nula da API
        when(httpClient.doHttpGet(anyString())).thenReturn(""); // ou então: when(httpClient.doHttpGet(anyString())).thenReturn(null);
        
        Optional<Product> product = productFinderService.findProductDetails(300);
        
        // Verifica que um Optional vazio é retornado
        assertFalse(product.isPresent());
    }


}
