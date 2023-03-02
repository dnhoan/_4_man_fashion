package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.entities.Product;

import java.util.List;

public interface ProductService {

    PageDTO<ProductDTO> getAll(int offset, int limit, Integer status, String search);

    List<Product> getListProduct();

    ProductDTO getById(Integer id);

    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    void updateStatus(Integer id, Integer status);
}
