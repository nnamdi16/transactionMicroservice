package com.nnamdi.transaction.controller;

import com.nnamdi.transaction.model.Transaction;
import com.nnamdi.transaction.service.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    Date date       = format.parse ( "2009-12-31" );

    public LoadDatabase() throws ParseException {
    }

    @Bean
    CommandLineRunner initDatabase(TransactionRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Transaction(1,2000.00,"Airtime Payment", 12345L,new Date() )));
        };
    }
}
