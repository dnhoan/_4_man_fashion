package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.dto.VoucherDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "voucher_code", nullable = false, length = 225)
    private String voucherCode;

    @Column(name = "voucher_name", nullable = false, length = 225)
    private String voucherName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "voucher_type")
    private int voucherType;

    @Column(name = "quantity")
    private float quantity;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private int status;

    public static Voucher fromDTO(VoucherDTO dto) {
        Voucher entity = new Voucher();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(1);

        return entity;
    }
}
