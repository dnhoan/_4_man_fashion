package com.example._4_man_fashion.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    private Integer id;

    private String customerName;

    private int gender;

    private String birthday;

    private String phoneNumber;

    private String address;

    private String email;


    private String avatar;

    private String note;

    private String ctime;

    private String mtime;

    private Integer status;

}
