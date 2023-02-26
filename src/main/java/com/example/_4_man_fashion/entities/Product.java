package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ProductDTO;
import lombok.*;
import org.hibernate.annotations.Type;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_name", nullable = false, length = 225)
    private String productName;

    @Column(name = "product_id", nullable = false, length = 20)
    private String productId;

    @Column(name = "description")
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Column(name = "details")
    @Type(type = "org.hibernate.type.TextType")
    private String detail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private Models model;

    @Column(name = "gender")
    private int gender;

    @Column(name = "material_name", nullable = false, length = 225)
    private String materialName;

    @Column(name = "category_name", nullable = false, length = 225)
    private String categoryName;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "status")
    private int status;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ProductDetail> productDetails;

    @PrePersist
    public void prePersist() {
        ctime = LocalDateTime.now();
        status = Constant.Status.ACTIVE;
        materialName = material.getMaterialName();
        categoryName = category.getCategoryName();
        modelName = model.getModelsName();
    }

    @PreUpdate
    public void preUpdate() {
        mtime = LocalDateTime.now();
        materialName = material.getMaterialName();
        categoryName = category.getCategoryName();
        modelName = model.getModelsName();
    }

    public static Product fromDTO(ProductDTO dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(1);

        return entity;
    }
}
