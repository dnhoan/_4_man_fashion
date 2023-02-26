package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.ProductDTO;

public interface ProductService {

    PageDTO<ProductDTO> getAll(int offset, int limit, Integer status, String search);

    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    void updateStatus(Integer id, Integer status);
}
