package com.cydeo.controller;

import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String getCreateForm(){
        // provide empty account object
        // account type enum needs to fill dropdown
        return "/account/create-account";
    }

    // create method to capture information from UI,
    // print them on the console
    // trigger createAccount method, create the account based on user input

}
