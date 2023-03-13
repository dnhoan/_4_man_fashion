package com.example._4_man_fashion.entities;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import com.example._4_man_fashion.dto.CartItemDTO;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "product_detail_id")
    private Integer productDetailId;

    public static CartItem fromDTO(CartItemDTO dto) {
        CartItem entity = new CartItem();
        BeanUtils.copyProperties(dto, entity);
        entity.setProductDetailId(dto.getProductDetailCartDto().getId());
        return entity;
    }
}
