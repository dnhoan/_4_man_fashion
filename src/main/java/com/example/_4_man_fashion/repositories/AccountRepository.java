package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByPhoneNumberOrEmail(String phoneNumber, String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);


}
