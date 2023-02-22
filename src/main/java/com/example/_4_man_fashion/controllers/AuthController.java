package com.example._4_man_fashion.controllers;

import com.example._4_man_fashion.Service.AuthService;
import com.example._4_man_fashion.configs.jwt.JwtUtils;
import com.example._4_man_fashion.configs.security.UserDetailsImpl;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Role;
import com.example._4_man_fashion.models.ERole;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.models.LoginRequest;
import com.example._4_man_fashion.models.SignupRequest;
import com.example._4_man_fashion.repositories.AccountRepository;
import com.example._4_man_fashion.repositories.RoleRepository;
import com.example._4_man_fashion.utils.ApiResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {



    @Autowired
    private AuthService authService;


    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

        JwtResponse jwtResponse = this.authService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.success(jwtResponse));
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signUpRequest) {

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

