package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account update(Account account) {
        return this.accountRepository.save(account);
    }

    @Override
    public Account findByPhoneNumber(String phoneNumber) {
        return this.accountRepository.findAccountByPhoneNumber(phoneNumber);
    }

    @Override
    public Account findByEmail(String email) {
        return this.accountRepository.findAccountByEmail(email);
    }
}
