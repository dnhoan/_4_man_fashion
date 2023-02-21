package com.example._4_man_fashion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelsDto {
    private int id;
    private String modelsName;
    private int status;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
