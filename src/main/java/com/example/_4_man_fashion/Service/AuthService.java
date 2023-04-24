package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.models.LoginRequest;
import com.example._4_man_fashion.models.SignupRequest;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    void signup(SignupRequest signupRequest);

    JwtResponse login(LoginRequest loginRequest);

    void forgot(String email);

    void resetPassWord(String email, String isOtp, String newPassword, String rePassword);

    void changePassWord(String phoneOrEmail, String password,String newPassword,String rePassword);
}
