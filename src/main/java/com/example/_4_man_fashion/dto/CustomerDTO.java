package com.example._4_man_fashion.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    private Integer id;

    private String customerName;

    private int gender;

    private LocalDate birthday;

    private String phoneNumber;

    private String address;

    private String email;

    private String avatar;

    private String note;

    private LocalDate ctime;

    private LocalDate mtime;

    private Integer status;

}
