package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    @GetMapping("/make-transfer")
    public String getMakeTransfer(){
        // what do we need to provide (to UI) to make the transfer happen?
        // --> we need to provide empty transaction object
        // --> we need all accounts (for the sender and receiver dropdowns)
        // --> we need list of transactions (last 10) to fill table. (business logic missing)

        return "/transaction/make-transfer";
    }
}
