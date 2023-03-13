package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.Size;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailCartDTO {
    private Integer id;
    private Float price;
    private Size size;
    private Color color;
    private int stock;
    private String productDetailName;
}
