package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.dto.VoucherDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "discount")
    private float discount;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "minimum_invoice_value")
    private float minimumInvoiceValue;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private int status;
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @OneToMany(mappedBy = "voucher", fetch = FetchType.LAZY)
    private List<Order> orders;
    public static Voucher fromDTO(VoucherDTO dto) {
        Voucher entity = new Voucher();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(1);

        return entity;
    }
}
