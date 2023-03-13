package com.example._4_man_fashion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private Integer id;
    private String voucherCode;
    private String voucherName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int voucherType;
    private float discount;
    private int quantity;
    private int maxDiscountValue;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
    private int status;
}
