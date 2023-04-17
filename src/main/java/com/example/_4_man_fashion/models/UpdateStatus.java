package com.example._4_man_fashion.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UpdateStatus {
    @NotEmpty
    private Integer id;
    @NotEmpty
    private Integer status;
}
