package com.example._4_man_fashion.models;


import com.example._4_man_fashion.entities.Exchange;
import com.example._4_man_fashion.entities.OrderDetails;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
public class ReturnOrderRequestBody {
    @NotEmpty
    OrderDetails orderDetails;
    @NotEmpty
    Integer orderId;
    @NotEmpty
    Integer currentOrderDetailId;
    @NotEmpty
    Integer statusOrder;
}