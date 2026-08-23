package com.paymentreconciliation.controller;

import com.paymentreconciliation.dto.DashboardResponse;
import com.paymentreconciliation.service.ReconciliationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")

    @CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://payment-reconciliation-frontend-mauve.vercel.app"
    }
)
public class DashboardController {

    private final ReconciliationService reconciliationService;

    public DashboardController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @GetMapping
    public DashboardResponse getDashboard() {
        return reconciliationService.getDashboard();
    }
}
