package com.example._4_man_fashion.DTOs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelsDto {
    private int id;
    private String modelsName;
    private int status;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
