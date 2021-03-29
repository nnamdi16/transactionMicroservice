package com.nnamdi.transaction.payload.request;

import java.util.Date;

public class TransactionRequest {
    private String transactionType;
    private Double amount;
    private String transactionDescription;
    private Long accountId;
    private Date date;

    public TransactionRequest(String transactionType, Double amount, String transactionDescription, Long accountId, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = new Date();
    }
}
