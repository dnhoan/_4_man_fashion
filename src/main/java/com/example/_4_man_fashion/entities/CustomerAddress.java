package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.dto.CustomerAddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_address")
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "ward_code")
    private int wardCode;

    @Column(name = "ward")
    private String ward;

    @Column(name = "district_code")
    private int districtCode;

    @Column(name = "district")
    private String district;

    @Column(name = "province_code")
    private int provinceCode;

    @Column(name = "province")
    private String province;

    @Column(name = "detail")
    private String detail;

    @Column(name = "status")
    private Integer status;

    public static CustomerAddress fromDTO(CustomerAddressDTO dto) {
        CustomerAddress entity = new CustomerAddress();
        BeanUtils.copyProperties(dto, entity);
        entity.setStatus(1);

        return entity;
    }
}
