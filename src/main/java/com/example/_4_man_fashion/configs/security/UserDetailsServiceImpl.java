package com.example._4_man_fashion.configs.security;

import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneOrEmail) throws UsernameNotFoundException {

        Account account = this.accountRepository.findByPhoneNumberOrEmail(phoneOrEmail,phoneOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or phone number: " + phoneOrEmail));
        return UserDetailsImpl.build(account);
    }
}
