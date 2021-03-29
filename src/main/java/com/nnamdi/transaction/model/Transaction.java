package com.nnamdi.transaction.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "transaction_id")
    Long transactionId;

    @Column(name = "transaction_type")
    private int transactionType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transaction_description")
    private String transactionDescription;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "date")
    private Date date;

    public Transaction(int transactionType, Double amount, String transactionDescription, Long accountId, Date date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDescription = transactionDescription;
        this.accountId = accountId;
        this.date = date;
    }

    public Transaction() {

    }


    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
