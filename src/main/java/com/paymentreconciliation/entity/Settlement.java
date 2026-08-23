package com.paymentreconciliation.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "settlements")
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Settlement ID is required")
    @Column(unique = true, nullable = false)
    private String settlementId;

    @NotBlank(message = "Transaction ID is required")
    @Column(nullable = false)
    private String transactionId;

    @NotNull(message = "Expected amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Expected amount must be greater than 0"
    )
    private BigDecimal expectedAmount;

    @NotNull(message = "Received amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Received amount must be greater than 0"
    )
    private BigDecimal receivedAmount;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDateTime settlementTime;

    public Settlement() {
    }

    public Long getId() {
        return id;
    }

    public String getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(LocalDateTime settlementTime) {
        this.settlementTime = settlementTime;
    }
}