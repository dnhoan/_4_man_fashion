package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

    @Query("select otp from Otp otp where otp.emailAccount = :email")
    Optional<Otp> findByEmail(@Param("email") String email);

    @Query("select otp from Otp otp where otp.phoneNumberAccount = :phoneNumber")
    Optional<Otp> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
