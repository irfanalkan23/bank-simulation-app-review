package com.cydeo.repository;

import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.exception.RecordNotFoundException;
import com.cydeo.dto.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository //this is not must
public interface AccountRepository extends JpaRepository<Account,Long> {

    //since we don't have a database now, we are simulating
//    public static List<AccountDTO> accountDTOList = new ArrayList<>();
//
//    public AccountDTO save(AccountDTO accountDTO){
//        accountDTOList.add(accountDTO);
//        return accountDTO;
//    }
//
//    public List<AccountDTO> findAll() {
//        return accountDTOList;
//    }
//
//    public AccountDTO findById(Long id) {
//        //write a method that finds the account inside the list, if not
//        //throws RecordNotFoundException
//
//        return accountDTOList.stream()
//                .filter(account->account.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RecordNotFoundException("Account not exist in the database."));
//    }

    //find accounts by status. make it dynamic instead of finding only ACTIVE accounts
    List<Account> findByAccountStatus(AccountStatus accountStatus);
}
