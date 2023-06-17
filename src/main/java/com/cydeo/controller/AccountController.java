package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller     // means "whenever user sends a request, (dispatch servlet) look at the methods inside this class"
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        model.addAttribute("accountList",accountService.listAllAccount());
        return "/account/index";
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model){
        // provide empty account object
        model.addAttribute("account", Account.builder().build());
        // account type enum needs to fill dropdown
        model.addAttribute("accountTypes", AccountType.values());
        return "/account/create-account";
    }

    // create method to capture information from UI,
    // print them on the console
    // trigger createAccount method, create the account based on user input
    @PostMapping("/create")
    public String getCreateForm2(@ModelAttribute("account") Account account){
        System.out.println(account.toString());
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(),account.getUserId());
        return "redirect:/index";
    }

}









