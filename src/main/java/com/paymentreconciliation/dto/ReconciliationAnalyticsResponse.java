
package com.paymentreconciliation.dto;

public class ReconciliationAnalyticsResponse {

    private long totalReconciliations;
    private long matched;
    private long amountMismatch;
    private long missingSettlement;

    public long getTotalReconciliations() {
        return totalReconciliations;
    }

    public void setTotalReconciliations(long totalReconciliations) {
        this.totalReconciliations = totalReconciliations;
    }

    public long getMatched() {
        return matched;
    }

    public void setMatched(long matched) {
        this.matched = matched;
    }

    public long getAmountMismatch() {
        return amountMismatch;
    }

    public void setAmountMismatch(long amountMismatch) {
        this.amountMismatch = amountMismatch;
    }

    public long getMissingSettlement() {
        return missingSettlement;
    }

    public void setMissingSettlement(long missingSettlement) {
        this.missingSettlement = missingSettlement;
    }
}