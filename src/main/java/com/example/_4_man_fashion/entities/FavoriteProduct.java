package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.dto.FavoriteProductDTO;
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
@Table(name="favorite_product")
public class FavoriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    public static FavoriteProduct fromDTO(FavoriteProductDTO dto) {
        FavoriteProduct entity = new FavoriteProduct();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());

        return entity;
    }
}
