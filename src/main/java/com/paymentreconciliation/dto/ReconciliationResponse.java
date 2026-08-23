package com.paymentreconciliation.dto;

public class ReconciliationResponse {

    private String transactionId;
    private String reconciliationStatus;
    private String message;

    public ReconciliationResponse() {
    }

    public ReconciliationResponse(String transactionId,
                                  String reconciliationStatus,
                                  String message) {
        this.transactionId = transactionId;
        this.reconciliationStatus = reconciliationStatus;
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReconciliationStatus() {
        return reconciliationStatus;
    }

    public void setReconciliationStatus(String reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}