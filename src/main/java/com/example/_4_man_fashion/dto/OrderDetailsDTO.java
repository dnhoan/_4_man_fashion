package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.ProductDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailsDTO {
    private int id;
    private int orderId;
    private float price;
    private int quantity;
    private int productDetailId;
    private int exchangeId;
    private int statusExchange;
    private int statusOrderDetail;
    private ProductDetail productDetail;
}
