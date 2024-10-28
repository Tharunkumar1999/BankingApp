package com.springboot.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.banking.dto.AccountDto;
import com.springboot.banking.entity.Account;
import com.springboot.banking.mapper.AccountMapper;
import com.springboot.banking.repository.AccountRepository;
import com.springboot.banking.service.AccountService;



@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
    Account account = accountRepository
                                .findById(id)
                                .orElseThrow(()->new RuntimeException("Account does't Exist"));
    return AccountMapper.mapToAccountDto(account);
    
    }

    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account = accountRepository
        .findById(id)
        .orElseThrow(()->new RuntimeException("Account does't Exist"));

        double total = account.getBalance() +amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = accountRepository
        .findById(id)
        .orElseThrow(()->new RuntimeException("Account does't Exist"));

        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient Balance");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        
       List<Account> accounts= accountRepository.findAll();
       return  accounts.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

}

