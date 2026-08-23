package com.paymentreconciliation.repository;

import com.paymentreconciliation.entity.Transaction;
import com.paymentreconciliation.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(String transactionId);

    long countByStatus(TransactionStatus status);
}