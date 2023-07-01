package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);
    //we didn't add account ID (id) as parameter. it will be randomly assigned (UUID)

    List<Account> listAllAccount();

    void deleteAccount(UUID id);
}
