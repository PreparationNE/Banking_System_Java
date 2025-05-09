package com.NE.Banking_System.repository;


import com.NE.Banking_System.entity.BankingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BankingTransactionRepository extends JpaRepository<BankingTransaction, Long> {

    List<BankingTransaction> findByCustomerId(Long customerId);
}

