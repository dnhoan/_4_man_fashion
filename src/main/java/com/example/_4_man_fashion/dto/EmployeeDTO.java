package com.example._4_man_fashion.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example._4_man_fashion.entities.Account;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private Integer id;
    private String employeeName;
    private String employeeCode;
    private int gender;
    private LocalDate birthday;
    private String phoneNumber;
    private String address;
    private String email;
    private String cccd;
    private float salary;
    private String image;
    private String note;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
    private Integer status;
    private Integer workType;
    private LocalDate timeOnboard;
    private LocalDate dayOff;
    private Account account;
}
