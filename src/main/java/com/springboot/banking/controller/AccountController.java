package com.springboot.banking.controller;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.banking.dto.AccountDto;
import com.springboot.banking.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //add new Account
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //get account Rest Api
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id ){
        return new ResponseEntity<>(accountService.getAccountById(id),HttpStatus.OK);
    }

    //deposit Rest Api
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit( @PathVariable Long id, @RequestBody Map<String, Double> request){
        return new ResponseEntity<>(accountService.deposit(id, request.get("amount")),HttpStatus.OK);
    }

    //withdraw amy Rest Api
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String,Double> request){
        return new ResponseEntity<>(accountService.withdraw(id, request.get("amount")),HttpStatus.OK);
    }

    //get All accounts
    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllAccount(){

        List<AccountDto> accounts=accountService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }

}
