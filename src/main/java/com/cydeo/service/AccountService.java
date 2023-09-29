package com.cydeo.service;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

//    AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);
//    //we didn't add account ID (id) as parameter. it will be randomly assigned (UUID)

    //above method changed into this one with orm:
    void createNewAccount(AccountDTO accountDTO);

    List<AccountDTO> listAllAccount();

    void deleteAccount(Long id);

    void activateAccount(Long id);

    AccountDTO retrieveById(Long id);
}
