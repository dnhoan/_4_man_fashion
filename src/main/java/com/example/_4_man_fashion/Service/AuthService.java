package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.models.LoginRequest;
import com.example._4_man_fashion.models.SignupRequest;

public interface AuthService {

    void signup(SignupRequest signupRequest);

    JwtResponse login(LoginRequest loginRequest);
}
