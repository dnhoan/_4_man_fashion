package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.entities.Account;

import java.util.Optional;

public interface AccountService {

    Account update(Account account);
    Account findByPhoneNumber(String phoneNumber);
    Account findByEmail(String email);

}
