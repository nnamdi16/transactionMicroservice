package com.nnamdi.transaction.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(Long id) {
        super("Could not find transaction " + id);
    }
}
