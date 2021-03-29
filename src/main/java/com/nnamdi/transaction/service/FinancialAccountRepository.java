package com.nnamdi.transaction.service;

import com.nnamdi.transaction.model.FinancialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialAccountRepository extends JpaRepository<FinancialAccount,Long> {

   Optional <FinancialAccount> findFinancialAccountByAccountId(Long accountId);
}
