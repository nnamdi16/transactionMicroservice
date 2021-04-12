package com.nnamdi.transaction.payload.request;

import javax.validation.constraints.NotBlank;


public class FinancialAccountRequest {

    @NotBlank
    private String accountId;

    @NotBlank
    private Double totalBalance;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
