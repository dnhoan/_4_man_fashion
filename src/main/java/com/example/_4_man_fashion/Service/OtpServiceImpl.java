package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.entities.Otp;
import com.example._4_man_fashion.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService{

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Otp save(Otp otp) {
        return this.otpRepository.save(otp);
    }

    @Override
    public Optional<Otp> findByEmail(String email) {
        return this.otpRepository.findByEmail(email);
    }

    @Override
    public Optional<Otp> findByPhoneNumber(String phoneNumber) {
        return this.otpRepository.findByPhoneNumber(phoneNumber);
    }
}
