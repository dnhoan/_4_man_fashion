package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Customer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDTO {
    private int id;
    private Customer customer;
    private int wardCode;
    private String ward;
    private int districtCode;
    private String district;
    private int provinceCode;
    private String province;
    private String detail;
    private int status;
    private String recipientName;
    private String recipientPhone;
    private String recipientEmail;
}
