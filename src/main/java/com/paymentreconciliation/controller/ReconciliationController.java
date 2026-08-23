package com.paymentreconciliation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymentreconciliation.dto.DashboardResponse;
import com.paymentreconciliation.dto.ReconciliationAnalyticsResponse;
import com.paymentreconciliation.dto.ReconciliationResponse;
import com.paymentreconciliation.entity.ReconciliationRecord;
import com.paymentreconciliation.service.ReconciliationService;

@RestController
@RequestMapping("/api/reconciliation")
@CrossOrigin(origins = "*")
public class ReconciliationController {

    private final ReconciliationService reconciliationService;

    public ReconciliationController(
            ReconciliationService reconciliationService) {

        this.reconciliationService = reconciliationService;
    }

    // Reconcile transaction
    @PostMapping("/{transactionId}")
    public ReconciliationResponse reconcileTransaction(
            @PathVariable String transactionId) {

        return reconciliationService.reconcile(transactionId);
    }

    // Dashboard
    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {

        return reconciliationService.getDashboard();
    }

    // Analytics
    @GetMapping("/analytics")
    public ReconciliationAnalyticsResponse getAnalytics() {

        return reconciliationService.getAnalytics();
    }

    // Reconciliation history
    @GetMapping("/history")
    public Page<ReconciliationRecord> getReconciliationHistory(
            @RequestParam(required = false) String status,
            Pageable pageable) {

        return reconciliationService
                .getReconciliationHistory(status, pageable);
    }
}