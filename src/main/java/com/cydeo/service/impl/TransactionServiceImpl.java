package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")         // this is how we read from application.properties !
    private boolean underConstruction;

//    private final AccountRepository accountRepository;
    //Between different service, don't communicate directly with other repository
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountRepository accountRepository, AccountService accountService, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
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
            TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            return transactionDTO;
        } else{
            throw new UnderConstructionException("App is under construction, try again later.");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if (checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            /* get the dto from database for both sender and receiver, update balance and save it
            create accountService updateAccount and use it for save */

            //find the sender account
            AccountDTO senderAcc = accountService.retrieveById(sender.getId());
            senderAcc.setBalance(sender.getBalance());
            //save again to database
            accountService.updateAccount(senderAcc);

            //find the receiver account
            AccountDTO receiverAcc = accountService.retrieveById(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());
            //save again to database
            accountService.updateAccount(receiverAcc);

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

    private AccountDTO findAccountById(Long id) {
        //I need accountRepository here; so, I inject private final AccountRepository above!
        //then, create constructor. add @Component
        return accountService.retrieveById(id);     //create findById() method in AccountRepository
    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        //get transaction entity for all and return them as a list of transactionDTO
        return transactionRepository.findAll().stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        // latest 10 transactions
        //write a native query to get the result for last 10 transactions
        //then convert it to dto and return
        return transactionRepository.findLast10Transactions().stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        //write a JPQL query to retrieve list of transactions by id
        //convert to dto and return
        return transactionRepository.findTransactionListById(id).stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }
}
