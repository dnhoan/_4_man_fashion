package com.example._4_man_fashion.configs.security;

import com.example._4_man_fashion.dto.CustomerDTO;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer id;

    private final String phoneNumber;

    private final String email;

    private final CustomerDTO customerDTO;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String phoneNumber, String email, String password,CustomerDTO customer,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.customerDTO = customer;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Account account) {
        List<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                account.getId(),
                account.getPhoneNumber(),
                account.getEmail(),
                account.getPassword(),
                UserDetailsImpl.customerMapToCustomerDTO(account.getCustomer()),
                authorities);
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public CustomerDTO getCustomerDto() {
        return customerDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static CustomerDTO customerMapToCustomerDTO(Customer customer) {

        return CustomerDTO.builder()
                .address(customer.getAddress())
                .customerName(customer.getCustomerName())
                .avatar(customer.getAvatar())
                .id(customer.getId())
                .gender(customer.getGender())
                .birthday(customer.getBirthday())
                .ctime(customer.getCtime().format(DateTimeFormatter.ISO_DATE_TIME))
                .mtime((customer.getMtime() == null) ? "" : customer.getMtime().format(DateTimeFormatter.ISO_DATE_TIME))
                .note(customer.getNote())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .status(customer.getStatus())
                .build();
    }
}
