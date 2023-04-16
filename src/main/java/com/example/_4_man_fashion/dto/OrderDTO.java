package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.OrderDetails;
import com.example._4_man_fashion.entities.Voucher;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private int id;

    private Integer customerId;
    private CustomerDTO customerInfo;
    private String orderId;

    private int orderStatus;

    private String recipientName;

    private String recipientPhone;

    private String recipientEmail;

    private String address;

    private float shipFee;

    private float goodsValue;

    private float checkout;

    private float sale;

    private float totalMoney;

    private int delivery;

    private Voucher voucher;

    private int purchaseType;

    private String note;

    private String cancelNote;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private List<OrderDetails> orderDetails;
    private List<LogOrderStatusDTO> logsOrderStatus;
}
