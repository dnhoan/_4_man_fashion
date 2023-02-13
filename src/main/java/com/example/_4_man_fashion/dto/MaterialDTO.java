package com.example._4_man_fashion.dto;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaterialDTO {
    private Integer id;
    private String materialName;
    private Integer status;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
