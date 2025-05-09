package com.NE.Banking_System.repository;

import com.NE.Banking_System.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAccountNumber(String accountNumber);

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);
}
