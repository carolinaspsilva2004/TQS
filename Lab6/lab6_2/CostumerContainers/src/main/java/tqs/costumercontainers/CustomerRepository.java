package tqs.costumercontainers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.costumercontainers.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Long> {
}