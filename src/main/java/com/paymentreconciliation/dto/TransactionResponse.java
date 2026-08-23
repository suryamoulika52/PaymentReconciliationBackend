package com.paymentreconciliation.dto;

public class TransactionResponse {

    private Long id;
    private String transactionId;
    private String status;
    private String message;

    public TransactionResponse() {
    }

    public TransactionResponse(Long id, String transactionId,
                               String status, String message) {
        this.id = id;
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}