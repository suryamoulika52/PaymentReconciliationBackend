package com.paymentreconciliation.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymentreconciliation.dto.DashboardResponse;
import com.paymentreconciliation.dto.ReconciliationAnalyticsResponse;
import com.paymentreconciliation.dto.ReconciliationResponse;
import com.paymentreconciliation.entity.ReconciliationRecord;
import com.paymentreconciliation.entity.Settlement;
import com.paymentreconciliation.entity.Transaction;
import com.paymentreconciliation.entity.TransactionStatus;
import com.paymentreconciliation.exception.ResourceNotFoundException;
import com.paymentreconciliation.repository.ReconciliationRecordRepository;
import com.paymentreconciliation.repository.SettlementRepository;
import com.paymentreconciliation.repository.TransactionRepository;

@Service
public class ReconciliationService {

    private final TransactionRepository transactionRepository;
    private final SettlementRepository settlementRepository;
    private final ReconciliationRecordRepository reconciliationRepository;

    public ReconciliationService(
            TransactionRepository transactionRepository,
            SettlementRepository settlementRepository,
            ReconciliationRecordRepository reconciliationRepository) {

        this.transactionRepository = transactionRepository;
        this.settlementRepository = settlementRepository;
        this.reconciliationRepository = reconciliationRepository;
    }


    // ==============================
    // RECONCILE TRANSACTION
    // ==============================

    public ReconciliationResponse reconcile(String transactionId) {

        // Find transaction
        Transaction transaction =
                transactionRepository
                        .findByTransactionId(transactionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found"
                                )
                        );

        // Check whether already reconciled
        ReconciliationRecord existingRecord =
                reconciliationRepository
                        .findFirstByTransactionIdOrderByReconciledAtDesc(
                                transactionId
                        )
                        .orElse(null);

        if (existingRecord != null) {

            return new ReconciliationResponse(
                    transactionId,
                    existingRecord.getReconciliationStatus(),
                    "Transaction already reconciled"
            );
        }


        // Find settlement
        Settlement settlement =
                settlementRepository
                        .findByTransactionId(transactionId)
                        .orElse(null);


        // Create reconciliation record
        ReconciliationRecord record =
                new ReconciliationRecord();

        record.setTransactionId(transactionId);

        record.setReconciledAt(
                LocalDateTime.now()
        );


        // ==============================
        // SETTLEMENT MISSING
        // ==============================

        if (settlement == null) {

            record.setSettlementId(null);

            record.setReconciliationStatus(
                    "MISSING_SETTLEMENT"
            );

            record.setMismatchReason(
                    "Settlement record not found"
            );

            reconciliationRepository.save(record);

            return new ReconciliationResponse(
                    transactionId,
                    "MISSING_SETTLEMENT",
                    "Settlement record not found"
            );
        }


        // Set settlement ID
        record.setSettlementId(
                settlement.getSettlementId()
        );


        // ==============================
        // AMOUNT MATCH
        // ==============================

        if (transaction.getAmount()
                .compareTo(
                        settlement.getReceivedAmount()
                ) == 0) {

            record.setReconciliationStatus(
                    "MATCHED"
            );

            record.setMismatchReason(null);

            reconciliationRepository.save(record);

            return new ReconciliationResponse(
                    transactionId,
                    "MATCHED",
                    "Transaction and settlement amounts match"
            );
        }


        // ==============================
        // AMOUNT MISMATCH
        // ==============================

        record.setReconciliationStatus(
                "AMOUNT_MISMATCH"
        );

        record.setMismatchReason(
                "Transaction amount and settlement amount do not match"
        );

        reconciliationRepository.save(record);

        return new ReconciliationResponse(
                transactionId,
                "AMOUNT_MISMATCH",
                "Amount mismatch detected"
        );
    }


    // ==============================
    // DASHBOARD
    // ==============================

    public DashboardResponse getDashboard() {

        DashboardResponse dashboard =
                new DashboardResponse();

        long totalTransactions =
                transactionRepository.count();

        long successfulTransactions =
                transactionRepository.countByStatus(
                        TransactionStatus.SUCCESS
                );

        long failedTransactions =
                transactionRepository.countByStatus(
                        TransactionStatus.FAILED
                );

        long pendingTransactions =
                transactionRepository.countByStatus(
                        TransactionStatus.PENDING
                );


        dashboard.setTotalTransactions(
                totalTransactions
        );

        dashboard.setSuccessfulTransactions(
                successfulTransactions
        );

        dashboard.setFailedTransactions(
                failedTransactions
        );

        dashboard.setPendingTransactions(
                pendingTransactions
        );

        return dashboard;
    }


    // ==============================
    // RECONCILIATION ANALYTICS
    // ==============================

    public ReconciliationAnalyticsResponse getAnalytics() {

        ReconciliationAnalyticsResponse analytics =
                new ReconciliationAnalyticsResponse();


        analytics.setTotalReconciliations(
                reconciliationRepository.count()
        );


        analytics.setMatched(
                reconciliationRepository
                        .countByReconciliationStatus(
                                "MATCHED"
                        )
        );


        analytics.setAmountMismatch(
                reconciliationRepository
                        .countByReconciliationStatus(
                                "AMOUNT_MISMATCH"
                        )
        );


        analytics.setMissingSettlement(
                reconciliationRepository
                        .countByReconciliationStatus(
                                "MISSING_SETTLEMENT"
                        )
        );


        return analytics;
    }


    // ==============================
    // RECONCILIATION HISTORY
    // PAGINATION + STATUS FILTER
    // ==============================

    public Page<ReconciliationRecord> getReconciliationHistory(
            String status,
            Pageable pageable) {

        if (status != null && !status.isBlank()) {

            return reconciliationRepository
                    .findByReconciliationStatusOrderByReconciledAtDesc(
                            status,
                            pageable
                    );
        }


        return reconciliationRepository
                .findAllByOrderByReconciledAtDesc(
                        pageable
                );
    }
}