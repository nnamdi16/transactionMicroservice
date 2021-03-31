package com.nnamdi.transaction.service;

import com.nnamdi.transaction.model.TransactionModelAssembler;
import org.springframework.stereotype.Service;

@Service
public class DepositService {

    protected static final long ACCOUNT_ID = 1L;
    private static final double MAX_DEPOSIT_PER_TRANSACTION = 40000;
    private static final double MAX_DEPOSIT_PER_DAY = 150000;
    private static final int MAX_DEPOSIT_TRANSACTION_PER_DAY = 10;
    private final DepositRepository depositRepository;
    private final TransactionModelAssembler transactionModelAssembler;
    private final FinancialAccountRepository financialAccountRepository;

    public DepositService(DepositRepository depositRepository, TransactionModelAssembler transactionModelAssembler, FinancialAccountRepository financialAccountRepository) {
        this.depositRepository = depositRepository;
        this.transactionModelAssembler = transactionModelAssembler;
        this.financialAccountRepository = financialAccountRepository;
    }


}
