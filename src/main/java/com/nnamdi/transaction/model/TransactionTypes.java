package com.nnamdi.transaction.model;

import javax.persistence.*;

@Entity
@Table(name = "transactionTypes")
public class TransactionTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionTypeId;

    @Enumerated(EnumType.STRING)
    private AccountTransactionTypes name;

    public TransactionTypes() {

    }

    public TransactionTypes(AccountTransactionTypes name) {
        this.name = name;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public AccountTransactionTypes getName() {
        return name;
    }

    public void setName(AccountTransactionTypes name) {
        this.name = name;
    }
}
