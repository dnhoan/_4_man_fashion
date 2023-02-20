package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductImageDTO {

    private Integer id;

    private Product product;

    private String image;
}
