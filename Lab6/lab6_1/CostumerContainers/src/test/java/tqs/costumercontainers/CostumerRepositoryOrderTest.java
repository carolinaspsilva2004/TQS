package tqs.costumercontainers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryOrderedTest {

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
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    @Order(1)
    void insertCustomer() {
        repository.save(new Customer(null, "John Doe", "john@example.com"));
    }

    @Test
    @Order(2)
    void retrieveCustomer() {
        List<Customer> customers = repository.findAll();
        assertFalse(customers.isEmpty());
        assertEquals("John Doe", customers.get(0).getName());
    }

    @Test
    @Order(3)
    void updateCustomer() {
        Customer customer = repository.findAll().get(0);
        customer.setName("Jane Doe");
        repository.save(customer);
    }

    @Test
    @Order(4)
    void retrieveUpdatedCustomer() {
        Customer updated = repository.findAll().get(0);
        assertEquals("Jane Doe", updated.getName());
    }
}
