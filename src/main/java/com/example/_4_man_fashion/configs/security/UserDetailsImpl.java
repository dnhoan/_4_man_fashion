package com.example._4_man_fashion.configs.security;

import com.example._4_man_fashion.dto.CustomerDTO;
import com.example._4_man_fashion.dto.EmployeeDTO;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDate;
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
    private final EmployeeDTO employeeDTO;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String phoneNumber, String email, String password,
                           CustomerDTO customer,
                           EmployeeDTO employeeDTO,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.customerDTO = customer;
        this.employeeDTO = employeeDTO;
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
                account.getCustomer() == null ? null : UserDetailsImpl.customerMapToCustomerDTO(account.getCustomer()),
                account.getEmployee() == null ? null : UserDetailsImpl.employeeMapToEmployeeDto(account.getEmployee()),
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
//                .birthday(customer.getBirthday() == null ? LocalDate.now() : customer.getBirthday())
//                .ctime(customer.getCtime() == null ? LocalDate.now() : customer.getCtime())
//                .mtime(customer.getMtime() == null ? LocalDate.now() : customer.getMtime())
                .note(customer.getNote())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .status(customer.getStatus())
                .build();
    }
    private static EmployeeDTO employeeMapToEmployeeDto(Employee employee) {
        return EmployeeDTO.builder()
                .address(employee.getAddress())
                .employeeName(employee.getEmployeeName())
                .employeeCode(employee.getEmployeeCode())
                .id(employee.getId())
//                    .birthday(customer.getBirthday() == null ? LocalDate.now() : customer.getBirthday())
//                .ctime(customer.getCtime() == null ? LocalDate.now() : customer.getCtime())
//                .mtime(customer.getMtime() == null ? LocalDate.now() : customer.getMtime())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .status(employee.getStatus())
                .build();
    }
}
