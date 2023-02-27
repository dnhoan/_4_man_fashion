package com.example._4_man_fashion.Service;

import java.util.List;

import com.example._4_man_fashion.dto.CartDTO;
import com.example._4_man_fashion.entities.Cart;

public interface CartService {
    List<CartDTO> getAll();

    CartDTO getCartByCustomerId(Integer cid);

    CartDTO create(CartDTO cartDTO);

    Cart update(Cart cart);

    void delete(Integer id);
}
