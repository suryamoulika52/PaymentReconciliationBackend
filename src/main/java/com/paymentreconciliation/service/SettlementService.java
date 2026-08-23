package com.paymentreconciliation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paymentreconciliation.entity.Settlement;
import com.paymentreconciliation.repository.SettlementRepository;

@Service
public class SettlementService {

    private final SettlementRepository settlementRepository;

    public SettlementService(
            SettlementRepository settlementRepository) {

        this.settlementRepository = settlementRepository;
    }

    public Settlement createSettlement(
            Settlement settlement) {

        return settlementRepository.save(settlement);
    }

    public List<Settlement> getAllSettlements() {

        return settlementRepository.findAll();
    }

    public Settlement getSettlementByTransactionId(
            String transactionId) {

        return settlementRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Settlement not found"
                        ));
    }
}