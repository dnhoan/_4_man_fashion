package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.entities.Product;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteProductDTO {
    private int id;
    private Customer customer;
    private Product product;
    LocalDateTime ctime;
}
