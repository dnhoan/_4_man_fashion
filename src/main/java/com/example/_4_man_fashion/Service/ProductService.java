package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.entities.Product;

public interface ProductService {

    PageDTO<ProductDTO> getAll(int offset, int limit, Integer status, String search);

    Product create(ProductDTO productDTO);

    Product update(ProductDTO productDTO);

    void delete(Integer id);
}
