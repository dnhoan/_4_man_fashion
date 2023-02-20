package com.example._4_man_fashion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialDTO {
    private Integer id;
    private String materialName;
    private Integer status;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
