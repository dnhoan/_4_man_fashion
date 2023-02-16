package com.example._4_man_fashion.DTOs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {
    private int id;
    private String sizeName;
    private int status;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
