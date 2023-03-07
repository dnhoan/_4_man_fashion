package com.example._4_man_fashion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDTO {
    private int id;
    private CustomerDTO customer;
    private int wardCode;
    private String ward;
    private int districtCode;
    private String district;
    private int provinceCode;
    private String province;
    private String detail;
    private int status;
}
