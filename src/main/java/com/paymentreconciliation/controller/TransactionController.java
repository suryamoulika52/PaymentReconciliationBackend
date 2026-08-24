package com.paymentreconciliation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.paymentreconciliation.dto.TransactionRequest;
import com.paymentreconciliation.dto.TransactionResponse;
import com.paymentreconciliation.entity.Transaction;
import com.paymentreconciliation.entity.TransactionStatus;
import com.paymentreconciliation.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://payment-reconciliation-frontend-mauve.vercel.app",
      
        "https://payment-reconciliation-fron-git-84ebb4-suryamoulika52s-projects.vercel.app"
    }
)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request) {

        return ResponseEntity.ok(
                transactionService.createTransaction(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(
                transactionService.getAllTransactions()
        );
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(
            @PathVariable String transactionId) {

        return ResponseEntity.ok(
                transactionService.getTransactionByTransactionId(transactionId)
        );
    }

    @PutMapping("/{transactionId}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable String transactionId,
            @RequestParam("status") TransactionStatus status) {

        return ResponseEntity.ok(
                transactionService.updateStatus(transactionId, status)
        );
    }
}
