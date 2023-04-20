package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.configs.jwt.JwtUtils;
import com.example._4_man_fashion.configs.security.UserDetailsImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.AccountDTO;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Otp;
import com.example._4_man_fashion.entities.Role;
import com.example._4_man_fashion.models.ERole;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.models.LoginRequest;
import com.example._4_man_fashion.models.SignupRequest;
import com.example._4_man_fashion.repositories.AccountRepository;
import com.example._4_man_fashion.repositories.OtpRepository;
import com.example._4_man_fashion.repositories.RoleRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public AccountService accountService;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        String phone = signupRequest.getPhoneNumber();

        Account account = new Account();

        if (email.matches(Constant.Regex.EMAIL)) {
            if (accountRepository.existsByEmail(email)) {
                throw new DATNException(ErrorMessage.OBJECT_ALREADY_EXIST.format("Email"));
            } else {
                account.setEmail(email);
            }
        }
        if (phone.matches(Constant.Regex.PHONE_NUMBER)) {
            if (accountRepository.existsByPhoneNumber(phone)) {
                throw new DATNException(ErrorMessage.OBJECT_ALREADY_EXIST.format("Số điện thoại"));
            } else {
                account.setPhoneNumber(phone);
            }
        }

        Set<Role> roles = new HashSet<>();
        String passwordEncrypt = this.passwordEncoder.encode(signupRequest.getPassword());

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Error: Role is not found.")));
        roles.add(userRole);

        account.setRoles(roles);
        account.setStatus(Constant.Status.ACTIVE);
        account.setPassword(passwordEncrypt);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Lỗi lưu vào db"));
        }

    }

    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        String phoneOrEmail = loginRequest.getPhoneOrEmail();
        String password = loginRequest.getPassword();
        if (phoneOrEmail.matches(Constant.Regex.EMAIL) || phoneOrEmail.matches(Constant.Regex.PHONE_NUMBER)) {
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(phoneOrEmail, password));
            } catch (Exception e) {
                e.printStackTrace();
                throw new DATNException(ErrorMessage.AUTH_USER_PASS_INVALID);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return JwtResponse
                    .builder()
                    .email(userDetails.getEmail())
                    .phoneNumber(userDetails.getUsername())
                    .token(jwt)
                    .build();
        }
        throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
    }

    @Override
    @Transactional
    public void forgot(String email) {
        Account account = this.accountRepository.findAccountByEmail(email);
        try {
            if (account == null) {
                throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Tài khoản"));
            } else {
                ExecutorService executor = Executors.newFixedThreadPool(10);
                Random random = new Random();
                int otp = random.nextInt(900000) + 100000;
                String subject = "Mã OTP xác thực tài khoản: ";
                String body = "Mã OTP của bạn là:" + otp;
                Otp isOtp = new Otp();
                isOtp.setOtpCode(String.valueOf(otp));
                isOtp.setEmailAccount(account.getEmail());
                isOtp.setStatus(1);
                isOtp.setIsUse_At(System.currentTimeMillis() + 300000);
                Optional<Otp> otp1 = this.otpRepository.findByEmail(email);
                if (otp1.isPresent()) {
                    isOtp.setId(otp1.get().getId());
                }
                this.otpRepository.save(isOtp);

                CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> sendMailService.sendSimpleEmail(email, subject, body), executor);
                executor.shutdown();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Lấy mã OTP thất bại"));
        }
    }

    @Override
    @Transactional
    public void resetPassWord(String email, String isOtp, String newPassword, String rePassword) {
        Account account = this.accountRepository.findAccountByEmail(email);
        if (account != null) {
            Optional<Otp> otp = this.otpRepository.findByEmail(email);
            if (otp.isPresent()) {
                if (otp.get().getOtpCode().equals(isOtp)) {
                    if (newPassword.equals(rePassword)) {
                        account.setPassword(passwordEncoder.encode(newPassword));
                        this.accountService.update(this.modelMapper.map(account, AccountDTO.class));
                    } else {
                        throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Mật khẩu xác nhận không chính xác!"));
                    }
                } else {
                    throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Mã OTP không chính xác. Vui lòng kiểm tra lại!"));
                }
            } else {
                throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Không tìm thấy mã OTP. Bạn vui lòng yêu cầu gửi lại mã!"));
            }
        } else {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Không tìm thấy tài khoản này!"));
        }

    }


    @Override
    @Transactional
    public void changePassWord(String email, String password, String newPassword, String rePassword) {
        List<Account> account = accountService.findByEmail(email);
        if (account != null) {
            boolean checkpw = passwordEncoder.matches(password, account.get(0).getPassword());
            if (checkpw == false) {
                throw new DATNException(ErrorMessage.PASSWORD_NOT_MATCH.format("Mật khẩu cũ"));

            }
            if (!newPassword.equals(rePassword)) {
                throw new DATNException(ErrorMessage.CUSTOM_ARGUMENT_NOT_VALID_V2.format("Mật khẩu xác nhận"));
            }
            if (password.equals(newPassword)) {
                throw new DATNException(ErrorMessage.REPASSWORD_NOT_DUPLICATE.format("Mật khẩu mới", "mật khẩu cũ"));
            }
            if (newPassword.equals(rePassword)) {
                account.get(0).setPassword(passwordEncoder.encode(newPassword));
                accountService.update(this.modelMapper.map(account, AccountDTO.class));
            }
        } else {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Tài khoản"));
        }

    }
}
