package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.configs.jwt.JwtUtils;
import com.example._4_man_fashion.configs.security.UserDetailsImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Role;
import com.example._4_man_fashion.models.ERole;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.models.LoginRequest;
import com.example._4_man_fashion.models.SignupRequest;
import com.example._4_man_fashion.repositories.AccountRepository;
import com.example._4_man_fashion.repositories.RoleRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        String phoneOrEmail = signupRequest.getPhoneOrEmail();

        Account account = new Account();

        if (phoneOrEmail.matches(Constant.Regex.EMAIL) ) {
            if(accountRepository.existsByEmail(signupRequest.getPhoneOrEmail()))
                throw new DATNException(ErrorMessage.OBJECT_ALREADY_EXIST.format("Email"));
            account.setEmail(phoneOrEmail);
        } else if (phoneOrEmail.matches(Constant.Regex.PHONE_NUMBER)) {
            if(accountRepository.existsByPhoneNumber(signupRequest.getPhoneOrEmail()))
                throw new DATNException(ErrorMessage.OBJECT_ALREADY_EXIST.format("Số điện thoại"));
            account.setPhoneNumber(phoneOrEmail);
        } else throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);

        Set<Role> roles = new HashSet<>();
        String passwordEncrypt = this.passwordEncoder.encode(signupRequest.getPassword());

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new DATNException(ErrorMessage.UNHANDLED_ERROR.format( "Error: Role is not found.")));
        roles.add(userRole);

        account.setRoles(roles);
        account.setStatus(Constant.Status.ACTIVE);
        account.setPassword(passwordEncrypt);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format( "Lỗi lưu vào db"));
        }

    }

    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        String phoneOrEmail = loginRequest.getPhoneOrEmail();

        if (phoneOrEmail.matches(Constant.Regex.EMAIL) || phoneOrEmail.matches(Constant.Regex.PHONE_NUMBER)) {
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phoneOrEmail, loginRequest.getPassword()));
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
        }  else
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
    }
}
