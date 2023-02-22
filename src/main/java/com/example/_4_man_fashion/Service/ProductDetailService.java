package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.dto.ProductDetailDTO;

import java.util.List;

public interface ProductDetailService {

    List<ProductDetailDTO> getProductDetailsByProductId(Integer product_id , Integer status);

}
