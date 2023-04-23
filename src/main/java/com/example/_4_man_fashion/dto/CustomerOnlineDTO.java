package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Account;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerOnlineDTO {
    private Integer id;

    private String customerName;

    private int gender;

    private LocalDate birthday;

    @NonNull
    private String phoneNumber;

    private String address;

    @NonNull
    private String email;

    private String avatar;

    private String note;

    private String ctime;

    private String mtime;

    private Integer status;
}
