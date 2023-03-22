package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ProductDetailDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.hibernate.annotations.Cascade;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "stock")
    private int stock;

    @Column(name = "product_detail_code")
    private String productDetailCode;

    @Column(name = "price", nullable = false, length = 225)
    private Float price;

    @Column(name = "size", nullable = false, length = 225)
    private String sizeName;

    @Column(name = "color_name", nullable = false, length = 225)
    private String colorName;

    @Column(name = "product_detail_name", nullable = false, length = 1000)
    private String productDetailName;

    // @Column(name = "product_id", nullable = false)
    // private Integer productId;

    @Column(name = "status")
    private int status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @OneToOne(mappedBy = "productDetail", fetch = FetchType.LAZY)
    private OrderDetails orderDetails;

    @PrePersist
    public void prePersist() {
        status = Constant.Status.ACTIVE;
        productDetailCode = "productDetailCode";
    }

    @PreUpdate
    public void preUpdate() {
        // materialName = material.getMaterialName();
        // categoryName = category.getCategoryName();
    }

    public static ProductDetail fromDTO(ProductDetailDTO dto) {
        ProductDetail entity = new ProductDetail();
        BeanUtils.copyProperties(dto, entity);
        entity.setStatus(1);

        return entity;
    }
}
