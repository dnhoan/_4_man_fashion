package com.example._4_man_fashion.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {
    private int id;
    private String size;
    private int status;
}
