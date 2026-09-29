package com.Vechile_Service.repo;

import com.Vechile_Service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerEmailIgnoreCase(String customerEmail);

    Optional<Customer> findByCustomerPhone(String customerPhone);
}
