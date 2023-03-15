package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.FavoriteProductDTO;
import com.example._4_man_fashion.entities.FavoriteProduct;

public interface FavoriteProductService {

    FavoriteProduct create(FavoriteProductDTO favoriteProductDTO);
    void delete(Integer id);

}
