package com.example._4_man_fashion.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderStatus {
    private Integer orderId;
    private Integer newStatus;
    private String note;
    private Integer userId;
}
