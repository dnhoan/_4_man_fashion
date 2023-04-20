package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Role;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private Integer id;
    private String email;
    private String password;
    private String phoneNumber;
    private Integer status;
    private Role role;

}
