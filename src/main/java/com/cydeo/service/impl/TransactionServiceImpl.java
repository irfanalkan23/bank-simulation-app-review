package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")         // this is how we read from application.properties !
    private boolean underConstruction;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        /*
        - if sender or receiver is null?
        - if sender and receiver is NOT same account?
        - if sender has enough balance?
        - if both accounts are checking, if not, one of them saving, it needs to be same user id
         */
        // if not under construction, execute the transaction
        if (!underConstruction){
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount,sender,receiver);

        /*
        After all validations are completed, and money is transferred, we need to create
        Transaction object and save/return it.
         */
            TransactionDTO transactionDTO = TransactionDTO.builder()
//                .sender(UUID.randomUUID())
                    .sender(sender.getId())
//                .receiver(UUID.randomUUID())
                    .receiver(receiver.getId())
                    .amount(amount)
                    .message(message)
                    .creationDate(creationDate)
                    .build();

            return transactionRepository.save(transactionDTO);
        } else{
            throw new UnderConstructionException("App is under construction, try again later.");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if (checkSenderBalance(sender,amount)){
            //make balance transfer between sender and receiver
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        } else {
            //throw BalanceNotSufficientException
            throw new BalanceNotSufficientException("Balance is not enough for this transfer.");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().compareTo(amount) >= 0;
//        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        // check if one of the accounts is saving, and user of sender and receiver are not the same
        // throw AccountOwnershipException
        if ((sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING))
        && !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnershipException("Since you are using a savings account, the sender and receiver userId must be different.");
        }
    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
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
    public List<TransactionDTO> findAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        // latest 10 transactions
        return transactionRepository.findLast10Transactions();
    }

    @Override
    public List<TransactionDTO> findTransactionListById(UUID id) {
        return transactionRepository.findTransactionListById(id);
    }
}
