package com.nnamdi.transaction.service;

import com.nnamdi.transaction.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface DepositRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByDateBetweenAndTransactionType(Date startOfDay, Date endOfDay, int transactionType);
}
