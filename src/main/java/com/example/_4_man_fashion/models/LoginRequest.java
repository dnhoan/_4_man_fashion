package com.example._4_man_fashion.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull
    @NotBlank
    private String phoneOrEmail;

    @NotNull
    @NotBlank
    private String password;
}
