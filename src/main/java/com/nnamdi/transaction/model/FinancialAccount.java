package com.nnamdi.transaction.model;

import javax.persistence.*;

@Entity
@Table(name = "financial_account")
public class FinancialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_id")
    private Long financialAccountId;

    @Column(name = "total_balance")
    private Double totalBalance;

    @Column(name = "account_id")
    private Long accountId;

    public FinancialAccount() {
    }

    public FinancialAccount(Double totalBalance, Long accountId) {
        this.totalBalance = totalBalance;
        this.accountId = accountId;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
