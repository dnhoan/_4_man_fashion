package com.example._4_man_fashion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private int id;
    private String voucherCode;
    private String voucherName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int voucherType;
    private float quantity;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
    private int status;
}
