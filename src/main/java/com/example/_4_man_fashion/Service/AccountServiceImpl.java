package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.entities.Role;
import com.example._4_man_fashion.models.ERole;
import com.example._4_man_fashion.repositories.AccountRepository;
import com.example._4_man_fashion.repositories.RoleRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private Integer isRoles = 1;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public PageDTO<AccountDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Account> page = this.accountRepository.getAll(pageable, status, StringCommon.getLikeCondition(search));
        List<AccountDTO> accountDTOList = page.stream().map(this::accoutMapAccountDTO).collect(Collectors.toList());
        return new PageDTO<AccountDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                accountDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Override
    public Account create(AccountDTO accountDTO) {
        if (StringCommon.isNullOrBlank(accountDTO.getPhoneNumber())) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Số điện thoại không được để trông!"));
        }
        if (StringCommon.isNullOrBlank(accountDTO.getEmail())) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Email không được để trông!"));
        }
        if (StringCommon.isNullOrBlank(accountDTO.getPassword())) {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Mật khẩu không được để trông!"));
        }
        List<Account> a;
        String email = accountDTO.getEmail();
        String phone = accountDTO.getPhoneNumber();
        if (email.matches(Constant.Regex.EMAIL)) {
            a = this.accountRepository.getAccountByEmail(email);
            if (a.size() == 2) {
                throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Email này đã đăng ký tài khoản!"));
            } else if (a.size() == 1 && a.get(0).getRoles()
                    .stream()
                    .anyMatch(r -> r.getName().equals(ERole.ROLE_ADMIN) || r.getName().equals(ERole.ROLE_EMPLOYEE))) {
                throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Email này đã đăng ký tài khoản!"));
            }
        }

        if (phone.matches(Constant.Regex.PHONE_NUMBER)) {
            a = this.accountRepository.getAccountByPhoneNumber(phone);
            if (a.size() == 2) {
                throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Số điện thoại này đã đăng ký tài khoản!"));
            } else if (a.size() == 1 && a.get(0).getRoles()
                    .stream()
                    .anyMatch(r -> r.getName().equals(ERole.ROLE_ADMIN) || r.getName().equals(ERole.ROLE_EMPLOYEE))) {
                throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Số điện thoại này đã đăng ký tài khoản!"));
            }
        }


        Set<Role> roles = new HashSet<>();

        String passwordEncrypt = this.passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(passwordEncrypt);

        accountDTO.setStatus(Constant.Status.ACTIVE);

        Account acc = this.mapAccountDTOtoAccount(accountDTO);
        roles.add(accountDTO.getRole());
        acc.setRoles(roles);
        return this.accountRepository.save(acc);
    }

    @Override
    public Account update(AccountDTO accountDTO) {
        Account account = this.accountRepository.getAccountById(accountDTO.getId());
        if (account != null) {
            if (accountRepository.existsByEmailAndIdIsNot(accountDTO.getEmail(), account.getId()) == true
                    && account.getPhoneNumber().equals(accountDTO.getPhoneNumber()) && accountDTO.getRole().equals(ERole.ROLE_USER)) {
                throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Email này đã đăng ký tài khoản!"));
            }
            if (accountRepository.existsByPhoneNumberAndIdIsNot(accountDTO.getPhoneNumber(), account.getId()) == true
                    && account.getPhoneNumber().equals(accountDTO.getPhoneNumber()) && accountDTO.getRole().equals(ERole.ROLE_USER)) {
                throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Số điện thoại này đã đăng ký tài khoản!"));
            }
            Set<Role> roles = new HashSet<>();
            roles.add(accountDTO.getRole());
            account.setId(accountDTO.getId());
            account.setEmail(accountDTO.getEmail());
            account.setPhoneNumber(accountDTO.getPhoneNumber());
            account.setRoles(roles);
            account.setStatus(accountDTO.getStatus());
            return this.accountRepository.save(account);
        } else {
            throw new DATNException(ErrorMessage.UNHANDLED_ERROR.format("Null Account"));

        }
    }

    @Override
    public void delete(Integer id) {
        Optional<Account> optionalAccount = this.accountRepository.findById(id);
        if (optionalAccount.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Account Id"));

        Account account = optionalAccount.get();
        account.setStatus(Constant.Status.INACTIVE);

        this.accountRepository.save(account);
    }

    @Override
    @Transactional
    public boolean restore(Integer accountId) {
        try {
            this.accountRepository.restoreAccount(accountId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account findByPhoneNumber(String phoneNumber) {
        return this.accountRepository.findAccountByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Account> findByEmail(String email) {
        return this.accountRepository.getListAccountByEmail(email);
    }

    private AccountDTO accoutMapAccountDTO(Account account) {
        AccountDTO accountDTO = this.modelMapper.map(account, AccountDTO.class);
        Role role = new Role();
        if (account.getRoles().stream().anyMatch(role1 -> role1.getName().equals(ERole.ROLE_ADMIN))) {
            role.setId(1);
            role.setName(ERole.ROLE_ADMIN);
        }
        if (account.getRoles().stream().anyMatch(role1 -> role1.getName().equals(ERole.ROLE_EMPLOYEE))) {
            role.setId(2);
            role.setName(ERole.ROLE_EMPLOYEE);
        }
        if (account.getRoles().stream().anyMatch(role1 -> role1.getName().equals(ERole.ROLE_USER))) {
            role.setId(3);
            role.setName(ERole.ROLE_USER);
        }
        accountDTO.setPassword("");
        accountDTO.setRole(role);
        return accountDTO;
    }

    private Account mapAccountDTOtoAccount(AccountDTO dto) {
        return this.modelMapper.map(dto, Account.class);
    }
}
