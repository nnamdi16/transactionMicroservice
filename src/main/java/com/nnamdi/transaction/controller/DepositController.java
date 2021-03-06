package com.nnamdi.transaction.controller;

import com.nnamdi.transaction.model.AccountTransactionTypes;
import com.nnamdi.transaction.model.FinancialAccount;
import com.nnamdi.transaction.model.Transaction;
import com.nnamdi.transaction.model.TransactionModelAssembler;
import com.nnamdi.transaction.payload.request.TransactionRequest;
import com.nnamdi.transaction.service.DepositRepository;
import com.nnamdi.transaction.service.FinancialAccountRepository;
import com.nnamdi.transaction.service.FinancialAccountService;
import com.nnamdi.transaction.service.MessageSenderService;
import com.nnamdi.transaction.payload.response.StandardJsonResponse;
import com.nnamdi.transaction.payload.response.StandardJsonResponseImpl;
import com.nnamdi.transaction.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deposit")
public class DepositController {

    Logger logger = LoggerFactory.getLogger(DepositController.class);
    @Autowired
    FinancialAccountService financialAccountService;

    private MessageChannel messageChannel;
    protected static final long ACCOUNT_ID = 1L;
    private static final double MAX_DEPOSIT_PER_TRANSACTION = 40000;
    private static final double MAX_DEPOSIT_PER_DAY = 150000;
    private static final int MAX_DEPOSIT_TRANSACTION_PER_DAY = 10;
    private final DepositRepository depositRepository;
    private final TransactionModelAssembler transactionModelAssembler;
    private final FinancialAccountRepository financialAccountRepository;


    public DepositController(DepositRepository depositRepository, TransactionModelAssembler transactionModelAssembler, FinancialAccountRepository financialAccountRepository, MessageSenderService messageSenderService) {
        this.depositRepository = depositRepository;
        this.transactionModelAssembler = transactionModelAssembler;
        this.financialAccountRepository = financialAccountRepository;
        messageChannel = messageSenderService.depositNotification();
    }

    @PostMapping("/")
    public ResponseEntity<?> makeDeposit(@Valid @RequestBody TransactionRequest transactionRequest) {
            StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();

        try{
            double total = 0;
            //check maximum limit deposit for the day has been reached.
            List<Transaction> deposits = depositRepository.findByDateBetweenAndTransactionType(TransactionUtils.getStartOfDay(new Date()),TransactionUtils.getEndOfDay(new Date()), AccountTransactionTypes.DEPOSIT.getId());
            if (deposits.size() > 0) {
                for (Transaction transaction:deposits) {
                    total += transaction.getAmount();
                }
                if (total + transactionRequest.getAmount() > MAX_DEPOSIT_PER_DAY) {
                    jsonResponse.setSuccess(false,"Error","Deposit for the day should not be more than $150K");
                    return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
                }
            }

            //Check whether the amount being deposited exceeds the MAX_DEPOSIT_PER_TRANSACTION
            if (transactionRequest.getAmount() > MAX_DEPOSIT_PER_TRANSACTION) {
                jsonResponse.setSuccess(false, "Error", "Deposit per transaction should not be more than $40K");
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
            }

            if (deposits.size() < MAX_DEPOSIT_TRANSACTION_PER_DAY) {
                Transaction transaction = new Transaction(AccountTransactionTypes.DEPOSIT.getId(), transactionRequest.getAmount(),transactionRequest.getTransactionDescription(),transactionRequest.getAccountId(), transactionRequest.getDate());
//                financialAccountService.sendAccountId(transaction.getAccountId());

                Optional<FinancialAccount> account = financialAccountRepository.findFinancialAccountByAccountId(transactionRequest.getAccountId());
                double newBalance = account.get().getTotalBalance() + transactionRequest.getAmount();
                account.get().setTotalBalance(newBalance);
                jsonResponse.setSuccess(true, "Success", "Deposit Successful");
                List<String> depositNotification = new ArrayList<>();
                String depositAmount = String.valueOf(transactionRequest.getAmount());
                String totalBalance = String.valueOf(newBalance);
                depositNotification.add(depositAmount);
                depositNotification.add(totalBalance);
                String accountId = String.valueOf(transaction.getAccountId());
                Message<String> msg = MessageBuilder.withPayload(accountId).build();
                this.messageChannel.send(msg);
                logger.info(String.valueOf(msg));
//                Message<List<String>> msg = MessageBuilder.withPayload(depositNotification).build();
//                this.messageChannel.send(msg);
                EntityModel<Transaction> entityModel = transactionModelAssembler.toModel(depositRepository.save(transaction));
                return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
            } else {
                jsonResponse.setSuccess(false, "Error","Maximum transaction for the day exceeded");
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
            }



        }catch (Exception ex) {
            jsonResponse.setSuccess(false, StandardJsonResponse.DEFAULT_MSG_TITLE_VALUE, StandardJsonResponse.DEFAULT_MSG_NAME_VALUE);
            return new ResponseEntity<>(jsonResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
