package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.entities.ProductDetail;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogOrderStatusDTO {
    private Integer id;

    private LocalDateTime times;

    private String user_change;

    private String note;

    private Integer currentStatus;

    private Integer newStatus;

}
