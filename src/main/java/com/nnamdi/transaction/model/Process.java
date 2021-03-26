package com.nnamdi.transaction.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Process {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long processId;
    private Double amount;
    private int transactionId;
    private String processType;
    private String processStatus;


    public Process() {
    }

    public Process(Double amount, int transactionId, String processType, String processStatus) {
        this.amount = amount;
        this.transactionId = transactionId;
        this.processType = processType;
        this.processStatus = processStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
}
