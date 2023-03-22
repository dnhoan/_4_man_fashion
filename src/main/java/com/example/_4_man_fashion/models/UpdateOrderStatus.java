package com.example._4_man_fashion.models;

import lombok.Data;

@Data
public class UpdateOrderStatus {
    private Integer id;
    private Integer newStatus;
    private String note;
}
