package com.example._4_man_fashion.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAPI {
    private String status;
    private String message;
    private Object object;
}
