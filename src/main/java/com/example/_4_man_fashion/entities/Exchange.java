package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.entities.ProductDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exchanges")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToMany(mappedBy = "exchange", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ExchangeImages> exchangeImages;

    @JsonIgnore
    @Transient
    @OneToOne(mappedBy = "exchange")
    private OrderDetails orderDetails;

    @Column(name = "reason")
    @Type(type = "org.hibernate.type.TextType")
    private String reason;

    @Column(name = "ctime")
    private LocalDateTime ctime;
}
