package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "gender")
    private int gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "note")
    private String note;

    @Column(name = "ctime")
    private LocalDate ctime;

    @Column(name = "mtime")
    private LocalDate mtime;

    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public static Customer fromDTO(CustomerDTO dto) {
        Customer entity = new Customer();
        BeanUtils.copyProperties(dto, entity);
        entity.setStatus(1);

        return entity;
    }
}
