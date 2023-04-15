package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.FavoriteProductDTO;
import com.example._4_man_fashion.dto.ModelsDto;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.FavoriteProduct;

public interface FavoriteProductService {

    PageDTO<FavoriteProductDTO> getAll(int offset, int limit, Integer status, String search);

    PageDTO<FavoriteProductDTO> getAllByCustomerId(int offset, int limit, Integer status, String search);

    FavoriteProduct create(FavoriteProductDTO favoriteProductDTO);
    void delete(Integer id);

}
