package com.example.service;

import java.util.*;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account){
        if(account.getUsername().isEmpty()){
            return null;
        } else if(account.getPassword().length() < 4){
            return null;
        } else {
            return accountRepository.save(account);
        }
    }

    public Optional<Account> login(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

    public Optional<Account> findByAccountUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public 
}
