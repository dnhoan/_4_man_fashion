package com.example._4_man_fashion.controllers;

import com.example._4_man_fashion.Service.AccountService;
import com.example._4_man_fashion.Service.AuthService;
import com.example._4_man_fashion.Service.OtpService;
import com.example._4_man_fashion.Service.SendMailService;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Otp;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.models.LoginRequest;
import com.example._4_man_fashion.models.SignupRequest;
import com.example._4_man_fashion.repositories.AccountRepository;
import com.example._4_man_fashion.utils.ApiResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("login")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

        JwtResponse jwtResponse = this.authService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.success(jwtResponse));
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody @Valid SignupRequest signUpRequest) {

        this.authService.signup(signUpRequest);

        return ResponseEntity.ok(ApiResponse.success("User registered successfully!"));
    }

    @GetMapping("forgot")
    public ResponseEntity<ApiResponse<String>> forgot(@RequestParam(value = "email") String email) {
        this.authService.forgot(email);
        return ResponseEntity.ok(ApiResponse.success("Mã OTP đã được gửi đến email của bạn!"));
    }

    @GetMapping("changepass")
    public ResponseEntity<ApiResponse<String>> changePass(@RequestParam(value = "email") String email,
                                                          @RequestParam(value = "password") String password,
                                                          @RequestParam(value = "newPassword") String newPassword,
                                                          @RequestParam(value = "repassword") String repassword
    ) {
        this.authService.changePassWord(email, password, newPassword, repassword);
        return ResponseEntity.ok(ApiResponse.success("Thay đổi mật khẩu thành công!"));
    }

}

@Data
class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
