package com.example._4_man_fashion.entities;

import javax.persistence.*;

import com.example._4_man_fashion.dto.AccountDTO;
import com.example._4_man_fashion.dto.ColorDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @JsonIgnore
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    public Account(String email, String password, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private Customer customer;

    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private Employee employee;

    @Column(name = "status")
    private Integer status;

    public static Account fromDTO(AccountDTO dto) {
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        entity.setPassword("");
        entity.setStatus(1);

        return entity;
    }

    public static AccountDTO fromEntity(Account account) {
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(account, dto);
        dto.setPassword("");
        dto.setStatus(1);

        return dto;
    }

}