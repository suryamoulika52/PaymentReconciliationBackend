package com.paymentreconciliation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymentreconciliation.dto.TransactionRequest;
import com.paymentreconciliation.dto.TransactionResponse;
import com.paymentreconciliation.entity.Transaction;
import com.paymentreconciliation.entity.TransactionStatus;
import com.paymentreconciliation.exception.ResourceNotFoundException;
import com.paymentreconciliation.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {

        if (transactionRepository
                .findByTransactionId(request.getTransactionId())
                .isPresent()) {

            throw new RuntimeException(
                    "Transaction ID already exists: "
                            + request.getTransactionId()
            );
        }

        Transaction transaction = new Transaction();

        transaction.setTransactionId(request.getTransactionId());

        transaction.setCustomerId(request.getCustomerId());

        transaction.setAmount(request.getAmount());

        transaction.setCurrency(request.getCurrency());

        transaction.setPaymentMethod(request.getPaymentMethod());

        transaction.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : TransactionStatus.PENDING
        );

        transaction.setTransactionTime(LocalDateTime.now());

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return new TransactionResponse(
                savedTransaction.getId(),
                savedTransaction.getTransactionId(),
                savedTransaction.getStatus().name(),
                "Transaction created successfully"
        );
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionByTransactionId(
            String transactionId
    ) {

        return transactionRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found: "
                                        + transactionId
                        )
                );
    }

    @Transactional
    public Transaction updateStatus(
            String transactionId,
            TransactionStatus status
    ) {

        Transaction transaction =
                transactionRepository
                        .findByTransactionId(transactionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found: "
                                                + transactionId
                                )
                        );

        transaction.setStatus(status);

        return transactionRepository.save(transaction);
    }
}