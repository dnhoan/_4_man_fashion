package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.entities.Otp;

import java.util.Optional;

public interface OtpService {

    Otp save(Otp otp);
    Optional<Otp> findByEmail(String email);
}
