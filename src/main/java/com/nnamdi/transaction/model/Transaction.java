package com.nnamdi.transaction.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Transaction {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;
    private String transactionType;
    private Double amount;
    private String transactionDescription;
    private Long accountId;

    public Transaction( String transactionType, Double amount, String transactionDescription, Long accountId) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDescription = transactionDescription;
        this.accountId = accountId;
    }

    public Transaction() {

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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.transactionId, this.accountId, this.amount, this.transactionDescription, this.transactionType);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + transactionId +
                ", accountId='" + accountId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount='" + amount + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Transaction))
            return false;

        Transaction transaction = (Transaction)obj;
        return Objects.equals(this.transactionId,transaction.transactionId) && Objects.equals(this.accountId,transaction.accountId);
    }
}
