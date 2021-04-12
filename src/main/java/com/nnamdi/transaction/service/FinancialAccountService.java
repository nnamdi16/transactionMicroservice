package com.nnamdi.transaction.service;

import com.nnamdi.transaction.model.FinancialAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableBinding(MessageService.class)
@Service
public class FinancialAccountService {
    private static final Double INITIAL_BALANCE = 0.00;
    private MessageChannel messageChannel;
    Logger logger = LoggerFactory.getLogger(FinancialAccountService.class);
//    public FinancialAccountService(MessageSenderService messageSenderService) {
//        messageChannel = messageSenderService.emailNotification();
//    }


    public FinancialAccountService() {
    }

    @Autowired
    FinancialAccountRepository financialAccountRepository;


    public FinancialAccountService(MessageSenderService messageSenderService) {
        messageChannel = messageSenderService.sendAccountId();
    }

    @StreamListener(target = MessageService.NOTIFICATION)
    public void createFinancialAccount(List<String> messages) throws MessagingException {

        Long accountId  = Long.parseLong(messages.get(0));
        FinancialAccount createAccount = new FinancialAccount();
        createAccount.setTotalBalance(INITIAL_BALANCE);
        createAccount.setAccountId(accountId);
        financialAccountRepository.save(createAccount);
//        Message<List<String>> msg = MessageBuilder.withPayload(messages).build();
//        this.messageChannel.send(msg);
        logger.info(String.valueOf(messages));
    }

//    public void sendAccountId(Long id) {
//        Message<Long> msg = MessageBuilder.withPayload(id).build();
//        this.messageChannel.send(msg);
//    }

    @StreamListener(target = MessageService.ACCOUNT_DETAILS_NOTIFICATION)
    public void fetchAccountDetails(String message) throws MessagingException {
        logger.info("Received Account Details: ",message);
        logger.info(message);

    }
}
