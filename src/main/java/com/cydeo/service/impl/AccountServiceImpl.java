package com.cydeo.service.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;    //inject AccountRepository

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create account object
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .balance(balance)
                .creationDate(creationDate)
                .accountType(accountType)
                .userId(userId)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        //save into the database
        //return the object created
        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();     //after creating DB, findAll() will be replaced by corresponding method name
    }
}
