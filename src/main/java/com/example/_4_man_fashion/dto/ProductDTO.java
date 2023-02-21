package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.Category;
import com.example._4_man_fashion.entities.Material;
import com.example._4_man_fashion.entities.Models;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Integer id;
    private String productName;
    private String productId;
    private String description;
    private String detail;
    private Material material;
    private Category category;
    private Models model;
    private int gender;
    private String materialName;
    private String categoryName;
    private String modelName;
    private int status;
    private Set<ProductDetailDTO> productDetails;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
