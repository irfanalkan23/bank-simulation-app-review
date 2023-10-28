package com.cydeo;

import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankSimulationAppReviewApplication {

    public static void main(String[] args) {
        //since we don't have the UI yet, we simulate the UI and check the app
        //create the container "ApplicationContext context = "
        ApplicationContext container = SpringApplication.run(BankSimulationAppReviewApplication.class, args);

        //the following part is for simulating the UI and testing the app
        //get account and transaction service beans
        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);

        //create accounts: sender and receiver
//        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70),new Date(), AccountType.CHECKING,1L);
//        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50),new Date(), AccountType.CHECKING,2L);
//        Account receiver2 = accountService.createNewAccount(BigDecimal.valueOf(432),new Date(), AccountType.SAVING,123L);
//        Account receiver3 = accountService.createNewAccount(BigDecimal.valueOf(6453),new Date(), AccountType.CHECKING,125L);

//        accountService.listAllAccount().forEach(System.out::println);
//
//        transactionService.makeTransaction(sender,receiver, new BigDecimal(40),new Date(),"Transaction 1");
//
//        System.out.println(transactionService.findAllTransaction().get(0));
//
//        accountService.listAllAccount().forEach(System.out::println);

    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    //we add PasswordEncoder because we don't want plain text password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
