package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.SizeDto;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
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
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_status", nullable = false)
    private int orderStatus;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone", nullable = false)
    private String recipientPhone;

    @Column(name = "recipient_email", nullable = false)
    private String recipientEmail;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "shipfee", nullable = false)
    private float shipFee;

    @Column(name = "goods_value", nullable = false)
    private float goodsValue;

    @Column(name = "checkout", nullable = false)
    private float checkout;

    @Column(name = "sale", nullable = false)
    private float sale;

    @Column(name = "total_money", nullable = false)
    private float totalMoney;

    @Column(name = "delivery", nullable = false)
    private int delivery;

    @Column(name = "purchase_type", nullable = false)
    private int purchaseType;

    @Column(name = "note", nullable = false)
    private String  note;

    @Column(name = "cancel_not", nullable = false)
    private String cancelNot;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private List<OrderDetails> orderDetails ;

    public static Order fromDTO(OrderDTO dto) {
        Order entity = new Order();
        BeanUtils.copyProperties(dto, entity);
        entity.setOrderStatus(1);

        return entity;
    }

}
