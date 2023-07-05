package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model){
        // what do we need to provide (to UI) to make the transfer happen?
        // --> we need to provide empty transaction object
        model.addAttribute("transaction", Transaction.builder().build());
        // --> we need all accounts (for the sender and receiver dropdowns)
        model.addAttribute("accounts", accountService.listAllAccount());
        // --> we need list of transactions (last 10) to fill table. (business logic missing)
        model.addAttribute("lastTransactions", transactionService.last10Transactions());

        return "/transaction/make-transfer";
    }

    // TASK
    // Write a post method, that takes transaction object from the method above
    // Complete the make transfer and return to the same page
    @PostMapping("/transfer")
    public String postMakeTransfer(@ModelAttribute("transfer") Transaction transaction){

        // I have UUID of accounts but I need to provide Account object
        // I need to find Accounts based on the ID that I have and use as a parameter to makeTransfer method

        Account sender = accountService.retrieveById(transaction.getSender());
        Account receiver = accountService.retrieveById(transaction.getReceiver());
        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),new Date(),transaction.getMessage());

        return "redirect:/make-transfer";
    }

}
