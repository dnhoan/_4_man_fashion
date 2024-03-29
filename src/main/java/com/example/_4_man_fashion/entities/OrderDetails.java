package com.example._4_man_fashion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "quantity_origin", nullable = false)
    private int quantityOrigin;

    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;


    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_id", nullable = false)
    private Exchange exchange;

//    @Column(name = "exchange_id")
//    private int exchangeId;
//
//    @Column(name = "status_exchange")
//    private int statusExchange;
//
    @Column(name = "status_order_detail")
    private int statusOrderDetail;
}
