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
@Table(name = "otp")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "email_account")
    private String emailAccount;

    @Column(name = "phoneNumber_account")
    private String phoneNumberAccount;

    @Column(name = "isuse_At")
    private Long isUse_At;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private Integer status;

}
