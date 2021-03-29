package com.nnamdi.transaction.model;

public enum AccountTransactionTypes {
    DEPOSIT(1),
    WITHDRAWAL(2),
    BILLPAY(3);
    private int id;

    AccountTransactionTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
