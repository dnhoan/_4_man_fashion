package com.example._4_man_fashion.dto;

import java.util.List;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
    private Integer id;
    private Double totalMoney;
    private Integer itemsAmount;
    private List<CartItemDTO> cartItemDtos;
}
