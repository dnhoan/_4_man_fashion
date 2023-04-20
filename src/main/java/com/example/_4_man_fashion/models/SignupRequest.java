package com.example._4_man_fashion.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class SignupRequest {

    @NotBlank
    @NotNull
    private String phoneNumber;

    @NotBlank
    @NotNull
    private String email;;

    @NotBlank
    @NotNull
    private String password;
}
