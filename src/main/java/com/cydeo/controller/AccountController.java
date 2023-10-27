package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.UUID;

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
        model.addAttribute("accountDTO", new AccountDTO());
        //replaced "account" with "accountDTO" (when implementing ORM)

        // account type enum needs to fill dropdown
        model.addAttribute("accountTypes", AccountType.values());
        return "/account/create-account";
    }

    // create method to capture information from UI,
    // print them on the console
    // trigger createAccount method, create the account based on user input
    @PostMapping("/create")
    public String getCreateForm2(@Valid @ModelAttribute("accountDTO") AccountDTO accountDTO, BindingResult bindingResult, Model model){
        //replaced "account" with "accountDTO" above (when implementing ORM)

        //BindingResult parameter should be right after @Valid object
        if (bindingResult.hasErrors()){
            //return the same page, and fill the dropdown (accountTypes)
            //we don't create an object (it will be below out of the if statement) if there is an error in filling the form
            model.addAttribute("accountTypes", AccountType.values());
            return "/account/create-account";
        }

        System.out.println(accountDTO);
        accountService.createNewAccount(accountDTO);
        // where is the account Id and accountStatus ?
        // they come from the builder : id(UUID.randomUUID()) and accountStatus(AccountStatus.ACTIVE)
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id){
        System.out.println(id);

        // trigger deleteAccount method
        accountService.deleteAccount(id);

        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") Long id){
        accountService.activateAccount(id);
        return "redirect:/index";
    }

}









