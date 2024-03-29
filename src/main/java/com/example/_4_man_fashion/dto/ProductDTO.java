package com.example._4_man_fashion.dto;

import com.example._4_man_fashion.entities.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Integer id;
    @NotNull
    @NotBlank
    private String productName;
    private String productId;
    private String description;
    private String detail;
    @NotNull
    private Material material;
    @NotNull
    private Category category;
    @NotNull
    private Models model;
    private int gender;
    private String materialName;
    private String categoryName;
    private String modelName;
    private int status;
    @NotEmpty
    private List<ProductImage> productImages;
    private List<ProductDetailDTO> productDetails;
    private List<Size> sizes;
    private List<Color> colors;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
    private boolean isExpand = false;
    private Size sizeSelected;
    private Color colorSelected;

    private int minPrice;
    private int maxPrice;
    private int amount = 0;

//    public void setProductImages(List<ProductImage> productImages) {
//        this.productImages = productImages.stream().map(ProductImage::getImage).collect(Collectors.toList());
//    }
}
