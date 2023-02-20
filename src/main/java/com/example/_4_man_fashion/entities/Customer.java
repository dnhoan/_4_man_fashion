package com.example._4_man_fashion.entities;

import lombok.*;

import javax.persistence.*;
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

    @Column(name = "birthDay")
    private String birthday;

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
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private Integer status;
}
