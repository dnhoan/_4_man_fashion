package com.example._4_man_fashion.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {
    private Integer id;
    private Integer amount;
    private ProductDetailDTO productDetailDTO;
//    private ProductDetailCartDTO productDetailCartDto;
}
