package com.nnamdi.transaction.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public interface MessageSenderService {
    @Output("depositNotification")
    MessageChannel depositNotification();

    @Output("sendAccountId")
    MessageChannel sendAccountId();



}
