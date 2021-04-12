package com.nnamdi.transaction.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageService {
    String NOTIFICATION = "notification";
    String ACCOUNT_DETAILS_NOTIFICATION = "accountDetails";

    @Input(NOTIFICATION)
    SubscribableChannel notification();

    @Input(ACCOUNT_DETAILS_NOTIFICATION)
    SubscribableChannel accountDetails();
}
