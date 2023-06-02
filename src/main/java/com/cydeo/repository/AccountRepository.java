package com.cydeo.repository;

import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {

    //since we don't have a database now, we are simulating
    public static List<Account> accountList = new ArrayList<>();

    public Account save(Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    public void findById(UUID id) {
        //write a method that finds the account inside the list, if not
        //throws RecordNotFoundException
    }
}
