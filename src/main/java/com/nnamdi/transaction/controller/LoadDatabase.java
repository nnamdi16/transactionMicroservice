package com.nnamdi.transaction.controller;

import com.nnamdi.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TransactionRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Transaction("BILL_PAY",2000.00,"Airtime Payment",23434L)));
        };
    }
}
