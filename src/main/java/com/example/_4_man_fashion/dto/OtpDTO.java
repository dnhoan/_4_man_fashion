package com.example._4_man_fashion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpDTO {
    private Integer id;

    private String otpCode;

    private String emailAccount;

    private String phoneNumberAccount;

    private Long isUse_At;

    private Integer status;
}
