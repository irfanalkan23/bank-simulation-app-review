package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.BadRequestException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountOwnershipException;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransaction(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        /*
        - if sender or receiver is null?
        - if sender and receiver is NOT same account?
        - if sender has enough balance?
        - if both accounts are checking, if not, one of them saving, it needs to be same user id
         */
        validateAccount(sender, receiver);
        checkAccountOwnership(sender, receiver);

        return null;
    }

    private void checkAccountOwnership(Account sender, Account receiver) {
        // check if one of the accounts is saving, and user of sender and receiver are not the same
        // throw AccountOwnershipException
        if ((sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING))
        && !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnershipException("Since you are using a savings account, the sender and receiver userId must be different.");
        }
    }

    private void validateAccount(Account sender, Account receiver) {
        /*
        - if any of the account is null
        - if account ids are the same (same account)
        - if the accounts exist in the database (repository)
         */

        if (sender==null||receiver==null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        if (sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }

        //verify if we have sender and receiver in database
        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private void findAccountById(UUID id) {
        //I need accountRepository here; so, I inject private final AccountRepository above!
        //then, create constructor. add @Component
        accountRepository.findById(id);     //create findById() method in AccountRepository
    }

    @Override
    public List<Account> findAllTransaction() {
        return null;
    }
}
