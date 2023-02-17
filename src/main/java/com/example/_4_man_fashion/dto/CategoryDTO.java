package com.example._4_man_fashion.dto;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private String categoryName;
    private Integer status;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
