package tqs.costumercontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
@SpringBootTest
public class FlywayMigrationTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CustomerRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testFlywayMigration() {
        // Verificar se a tabela de clientes contém dados após a execução do Flyway
        var customers = repository.findAll();
        
        // Verificar se os clientes foram inseridos corretamente
        assertFalse(customers.isEmpty());
        assertEquals(3, customers.size());  // Espera-se 3 registros de exemplo inseridos
        assertEquals("John Doe", customers.get(0).getName());
    }
}
