package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;    //inject AccountRepository
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {

        // we will complete the DTO, because UI is not providing all the fields
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountDTO.setCreationDate(new Date());

        // convert it to entity and save it
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

    @Override
    public List<AccountDTO> listAllAccount() {

        /* we are getting list of account from repo (database)
        but we need to return list of AccountDTO to controller
        what we need to do is we will convert Accounts to AccountDTO
         */
        List<Account> accountList = accountRepository.findAll();     //after creating DB, findAll() will be replaced by corresponding method name
        //converting list of account to accountDTOs and returning it
        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account object based on id
        Account account = accountRepository.findById(id).get();
        //update the accountStatus of that object
        account.setAccountStatus(AccountStatus.DELETED);
        //save the updated account object in database.
        accountRepository.save(account);
    }

    @Override
    public void activateAccount(Long id) {
        //find the account object based on id
        Account account = accountRepository.findById(id).get();
        //update the accountStatus of that object
        account.setAccountStatus(AccountStatus.ACTIVE);
        //save the updated account object in database.
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        //find the account entity by id, convert to dto and return
        return accountMapper.convertToDTO(accountRepository.findById(id).get());
    }

    //return only the active accounts from database
    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        //get active accounts from repository
        List<Account> accountList = accountRepository.findByAccountStatus(AccountStatus.ACTIVE);
        //convert active accounts to dto and return
        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }


}
