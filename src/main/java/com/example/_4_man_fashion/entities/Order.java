package com.example._4_man_fashion.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "customer_id")
    private Integer customerId;

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
    private String note;

    @Column(name = "cancel_not", nullable = false)
    private String cancelNote;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @OrderBy()
    @Where(clause = "status_order_detail != 0")
    private List<OrderDetails> orderDetails;

}
