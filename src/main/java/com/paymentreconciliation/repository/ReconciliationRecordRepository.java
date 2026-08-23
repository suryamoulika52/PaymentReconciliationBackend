package com.paymentreconciliation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentreconciliation.entity.ReconciliationRecord;

public interface ReconciliationRecordRepository
        extends JpaRepository<ReconciliationRecord, Long> {


    long countByReconciliationStatus(
            String reconciliationStatus
    );


    Optional<ReconciliationRecord>
    findFirstByTransactionIdOrderByReconciledAtDesc(
            String transactionId
    );


    Page<ReconciliationRecord>
    findAllByOrderByReconciledAtDesc(
            Pageable pageable
    );


    Page<ReconciliationRecord>
    findByReconciliationStatusOrderByReconciledAtDesc(
            String reconciliationStatus,
            Pageable pageable
    );
}