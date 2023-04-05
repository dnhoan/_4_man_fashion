package com.example._4_man_fashion.entities;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "otpCode")
    private Integer otpCode;

    @Column(name = "emailAccount")
    private String emailAccount;

    @Column(name = "issue_At")
    private Long issue_At;

    @Column(name = "status")
    private Integer status;

}
