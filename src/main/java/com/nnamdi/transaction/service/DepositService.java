package com.nnamdi.transaction.service;

import com.nnamdi.transaction.model.AccountTransactionTypes;
import com.nnamdi.transaction.model.FinancialAccount;
import com.nnamdi.transaction.model.Transaction;
import com.nnamdi.transaction.model.TransactionModelAssembler;
import com.nnamdi.transaction.util.StandardJsonResponse;
import com.nnamdi.transaction.util.StandardJsonResponseImpl;
import com.nnamdi.transaction.util.TransactionUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public StandardJsonResponse depositFunds(Double amount, String transactionDescription, Long accountId, Date date) {
        StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();
        try {
            double total = 0;
            //check maximum limit deposit for the day has been reached.
            List<Transaction> deposits = depositRepository.findByDateBetweenAndTransactionType(TransactionUtils.getStartOfDay(new Date()),TransactionUtils.getEndOfDay(new Date()), AccountTransactionTypes.DEPOSIT.getId());
            if (deposits.size() > 0) {
                for (Transaction transaction:deposits) {
                    total += transaction.getAmount();
                }
                if (total + amount > MAX_DEPOSIT_PER_DAY) {
                    jsonResponse.setSuccess(false,"Error","Deposit for the day should not be more than $150K");
                    return jsonResponse;
                }
            }
            //Check whether the amount being deposited exceeds the MAX_DEPOSIT_PER_TRANSACTION
            if (amount > MAX_DEPOSIT_PER_TRANSACTION) {
                jsonResponse.setSuccess(false, "Error", "Deposit per transaction should not be more than $40K");
                return jsonResponse;
            }
            if (deposits.size() < MAX_DEPOSIT_TRANSACTION_PER_DAY) {
                Transaction transaction = new Transaction(AccountTransactionTypes.DEPOSIT.getId(), amount,transactionDescription,accountId, date);
//                financialAccountService.sendAccountId(transaction.getAccountId());

                Optional<FinancialAccount> account = financialAccountRepository.findFinancialAccountByAccountId(accountId);
                double newBalance = account.get().getTotalBalance() + amount;
                account.get().setTotalBalance(newBalance);
                jsonResponse.setSuccess(true, "Success", "Deposit Successful");
                return (StandardJsonResponse) transaction;
            }else {
                jsonResponse.setSuccess(false, "Error","Maximum transaction for the day exceeded");
                return jsonResponse;
            }

        } catch (Exception e) {
            jsonResponse.setSuccess(false, StandardJsonResponse.DEFAULT_MSG_TITLE_VALUE, StandardJsonResponse.DEFAULT_MSG_NAME_VALUE);
            return  jsonResponse;
        }
    }


}
