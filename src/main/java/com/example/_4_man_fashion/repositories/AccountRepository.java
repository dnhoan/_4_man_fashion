package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByPhoneNumberOrEmail(String phoneNumber, String email);

    @Query("select a from Account a join Customer cus on cus.account.id = a.id where cus.id =:cusId")
    Optional<Account> getAccountByCustomerId(Integer cusId);

    @Query("select a from Account a where a.phoneNumber =:phoneNumber")
    Account findAccountByPhoneNumber(String phoneNumber);

    @Query("select a from Account a where a.email =:email")
    Account findAccountByEmail(String email);


    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);


}
