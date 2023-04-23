package com.example._4_man_fashion.entities;

import lombok.*;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import com.example._4_man_fashion.dto.EmployeeDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "employee_code", nullable = false)
    private String employeeCode;

    @Column(name = "gender")
    private int gender;

    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "salary")
    private float salary;

    @Column(name = "time_onboard")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeOnboard;

    @Column(name = "day_off")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOff;

    @Column(name = "image")
    private String image;

    @Column(name = "note")
    private String note;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "work_type")
    private Integer workType;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public static Employee fromDTO(EmployeeDTO dto) {
        Employee entity = new Employee();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(dto.getStatus());
        entity.setAccount(dto.getAccount());

        return entity;
    }
}
