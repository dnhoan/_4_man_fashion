package com.example._4_man_fashion.controllers;

import com.example._4_man_fashion.Service.AuthService;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.models.LoginRequest;
import com.example._4_man_fashion.models.SignupRequest;
import com.example._4_man_fashion.utils.ApiResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request) {

        JwtResponse jwtResponse = this.authService.login(loginRequest, request);

        return ResponseEntity.ok(ApiResponse.success(jwtResponse));
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody @Valid SignupRequest signUpRequest) {

        this.authService.signup(signUpRequest);

        return ResponseEntity.ok(ApiResponse.success("User registered successfully!"));
    }
}

@Data
class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
