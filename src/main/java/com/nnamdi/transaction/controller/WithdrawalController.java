package com.nnamdi.transaction.controller;

import com.nnamdi.transaction.model.AccountTransactionTypes;
import com.nnamdi.transaction.model.FinancialAccount;
import com.nnamdi.transaction.model.Transaction;
import com.nnamdi.transaction.model.TransactionModelAssembler;
import com.nnamdi.transaction.payload.request.TransactionRequest;
import com.nnamdi.transaction.service.DepositRepository;
import com.nnamdi.transaction.service.FinancialAccountRepository;
import com.nnamdi.transaction.util.StandardJsonResponse;
import com.nnamdi.transaction.util.StandardJsonResponseImpl;
import com.nnamdi.transaction.util.TransactionUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {
    private static final double MAX_WITHDRAWAL_PER_TRANSACTION = 20000; // $20k
    private static final double MAX_WITHDRAWAL_PER_DAY = 50000; // $50k
    private static final int MAX_WITHDRAWAL_TRANSACTIONS_PER_DAY = 3;
    private final DepositRepository depositRepository;
    private final TransactionModelAssembler transactionModelAssembler;
    private final FinancialAccountRepository financialAccountRepository;

    public WithdrawalController(DepositRepository depositRepository, TransactionModelAssembler transactionModelAssembler, FinancialAccountRepository financialAccountRepository) {
        this.depositRepository = depositRepository;
        this.transactionModelAssembler = transactionModelAssembler;
        this.financialAccountRepository = financialAccountRepository;
    }

    @PostMapping("/")
    public ResponseEntity<?> makeWithdrawal(@Valid @RequestBody TransactionRequest transactionRequest) {
        StandardJsonResponse jsonResponse;
        jsonResponse = new StandardJsonResponseImpl();

        try{
            double total = 0;
            //check Balance
            double balance = financialAccountRepository.findFinancialAccountByAccountId(transactionRequest.getAccountId()).get().getTotalBalance();
            if (transactionRequest.getAmount() >balance) {
                jsonResponse.setSuccess(false, "Error", "Insufficient funds");
                return new ResponseEntity<>(jsonResponse,HttpStatus.NOT_ACCEPTABLE);
            }
            //check maximum limit deposit for the day has been reached.
            List<Transaction> withdrawals = depositRepository.findByDateBetweenAndTransactionType(TransactionUtils.getStartOfDay(new Date()),TransactionUtils.getEndOfDay(new Date()), AccountTransactionTypes.WITHDRAWAL.getId());
            if (withdrawals.size() > 0) {
                for (Transaction transaction:withdrawals) {
                    total += transaction.getAmount();
                }
                if (total + transactionRequest.getAmount() > MAX_WITHDRAWAL_PER_DAY) {
                    jsonResponse.setSuccess(false,"Error","Withdrawal per day should not be more than $50K");
                    return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
                }
            }

            // Check whether the amount being withdrawn exceeds the MAX_WITHDRAWAL_PER_TRANSACTION
            if (transactionRequest.getAmount() > MAX_WITHDRAWAL_PER_TRANSACTION ) {
                jsonResponse.setSuccess(false, "Error", "Exceeded Maximum Withdrawal Per Transaction");
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
            }

            // check whether transactions exceeds the max allowed per day
            if (withdrawals.size() < MAX_WITHDRAWAL_TRANSACTIONS_PER_DAY) {
                Transaction transaction = new Transaction(AccountTransactionTypes.WITHDRAWAL.getId(), transactionRequest.getAmount(),transactionRequest.getTransactionDescription(),transactionRequest.getAccountId(), transactionRequest.getDate());
                Optional<FinancialAccount> account = financialAccountRepository.findFinancialAccountByAccountId(transactionRequest.getAccountId());
                double newBalance = account.get().getTotalBalance() - transactionRequest.getAmount();
                account.get().setTotalBalance(newBalance);
                jsonResponse.setSuccess(true, "Success", "Withdrawal Successful");
                EntityModel<Transaction> entityModel = transactionModelAssembler.toModel(depositRepository.save(transaction));
                return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
            } else {
                jsonResponse.setSuccess(false, "Error","Maximum Withdrawal transactions for the day Exceeded");
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
            }



        }catch (Exception ex) {
            jsonResponse.setSuccess(false, StandardJsonResponse.DEFAULT_MSG_TITLE_VALUE, StandardJsonResponse.DEFAULT_MSG_NAME_VALUE);
            return new ResponseEntity<>(jsonResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


}
