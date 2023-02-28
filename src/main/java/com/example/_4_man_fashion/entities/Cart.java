package com.example._4_man_fashion.entities;

import java.util.List;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import com.example._4_man_fashion.dto.CartDTO;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "items_amount")
    private Integer itemsAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems;

    public static Cart fromDTO(CartDTO dto) {
        Cart entity = new Cart();
        BeanUtils.copyProperties(dto, entity);

        return entity;
    }
}
