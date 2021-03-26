package com.nnamdi.transaction.payload.request;

public class TransactionRequest {
    private String transactionType;
    private Double amount;
    private String transactionDescription;
    private Long accountId;

    public TransactionRequest(String transactionType, Double amount, String transactionDescription, Long accountId) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDescription = transactionDescription;
        this.accountId = accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
