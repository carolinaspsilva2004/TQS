package tqs.mockforhttpclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFinderServiceIT {

    private ProductFinderService productFinderService;

    @BeforeEach
    void setUp() {
        // Usando o cliente HTTP real para testes de integração
        ISimpleHttpClient realHttpClient = new TqsBasicHttpClient();
        productFinderService = new ProductFinderService(realHttpClient);
    }

    @Test
    void testFindProductDetails_ValidProduct() {
        // Requisição para o produto com ID 3
        Optional<Product> product = productFinderService.findProductDetails(3);
        System.out.println("Product: " + product);  // Adicionando log para depuração

        // Verificando se o produto é encontrado com os detalhes corretos
        assertTrue(product.isPresent(), "Product should be present.");
        assertEquals(3, product.get().getId(), "Product ID should match.");
        assertEquals("Mens Cotton Jacket", product.get().getTitle(), "Product title should match.");
    }

    @Test
    void testFindProductDetails_ProductNotFound() {
        // Produto com ID 999, que se assume que não existe
        Optional<Product> product = productFinderService.findProductDetails(999);

        // Verificando que nenhum produto é retornado para um ID não existente
        assertFalse(product.isPresent(), "Product should not be present.");
    }

    @Test
    void testFindProductDetails_EmptyResponse() {
        // Simulando uma resposta vazia da API com um ID não existente
        Optional<Product> product = productFinderService.findProductDetails(1000000);

        // Verificando que nenhum produto é retornado para um ID não existente
        assertFalse(product.isPresent(), "Product should not be present.");
    }
}
