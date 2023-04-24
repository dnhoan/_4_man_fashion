package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Role;
import com.example._4_man_fashion.models.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select acc from Account acc where acc.phoneNumber = :search " +
            "or (lower(acc.email) like  '%' || lower(:search) || '%') " +
            "and (:status = -1 or acc.status = :status) order by acc.id")
    Page<Account> getAll(Pageable pageable, Integer status, @Param("search") String search);

    Optional<Account> findByPhoneNumberOrEmail(String phoneNumber, String email);

    @Query("select a from Account a join Customer cus on cus.account.id = a.id where cus.id =:cusId")
    Optional<Account> getAccountByCustomerId(Integer cusId);

    @Query("select a from Account a where a.phoneNumber =:phoneNumber")
    Account findAccountByPhoneNumber(String phoneNumber);

    @Query("select a from Account a where a.email =:email")
    Account findAccountByEmail(String email);

    @Query("select a from Account a where a.email =:email")
    List<Account> getListAccountByEmail(String email);

    @Query("select a from Account a where a.email =:phoneOrEmail or a.phoneNumber =: phoneOrEmail")
    Account getListAccountByEmailOrPhone(String phoneOrEmail);

    @Query("select a from Account a where a.email =:email")
    List<Account> getAccountByEmail(String email);
    @Query("select a from Account a where a.phoneNumber =:phoneNumber")
    List<Account> getAccountByPhoneNumber(String phoneNumber);
    @Query("select a from Account a where a.id =:id")
    Account getAccountById(Integer id);

    Boolean existsByEmail(String email);
    Boolean existsAccountByEmailAndRoles(String email, Role role);

    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsAccountByPhoneNumberAndRoles(String phoneNumber, Role role);

    boolean existsByEmailAndIdIsNot(String email, Integer id);

    boolean existsByPhoneNumberAndIdIsNot(String phoneNumber, Integer id);

    @Modifying
    @Query("update Account acc set acc.status = 1 where acc.id = :id")
    void restoreAccount(@Param("id") Integer id);

}
