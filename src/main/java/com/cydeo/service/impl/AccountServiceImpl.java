package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
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
    public AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create account object
        AccountDTO accountDTO = new AccountDTO(); // TODO: 28/09/2023  

        //save into the database
        //return the object created
        return accountRepository.save(accountDTO);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll();     //after creating DB, findAll() will be replaced by corresponding method name
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account object based on id
        AccountDTO accountDTO = accountRepository.findById(id);
        //update the accountStatus of that object
        accountDTO.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(Long id) {
        //find the account object based on id
        AccountDTO accountDTO = accountRepository.findById(id);
        //update the accountStatus of that object
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return accountRepository.findById(id);
    }
}
