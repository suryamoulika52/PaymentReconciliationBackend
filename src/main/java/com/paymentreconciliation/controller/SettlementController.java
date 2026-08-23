package com.paymentreconciliation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.paymentreconciliation.entity.Settlement;
import com.paymentreconciliation.service.SettlementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/settlements")
@CrossOrigin(origins = "*")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(
            SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Settlement createSettlement(
    		 @Valid  @RequestBody Settlement settlement) {

        return settlementService.createSettlement(settlement);
    }

    @GetMapping
    public List<Settlement> getAllSettlements() {
        return settlementService.getAllSettlements();
    }
}