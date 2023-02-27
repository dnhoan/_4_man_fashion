package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.CartItemDTO;

public interface CartItemService {
    CartItemDTO addCartItem(CartItemDTO cartItemDTO, Integer customerId);

    CartItemDTO updateCartItem(CartItemDTO cartItemDTO, Integer cart_id);

    void deleteCartItem(Integer id);
}
