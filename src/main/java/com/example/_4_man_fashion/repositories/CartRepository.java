package com.example._4_man_fashion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example._4_man_fashion.dto.ProductDetailDTO;
import com.example._4_man_fashion.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c where c.customer.id = :customer_id")
    Optional<Cart> getCartByCustomerId(@Param("customer_id") Integer customer_id);

    @Query("select pd from ProductDetail pd join CartItem c on pd.id = c.productDetailId where c.id = :cart_item_id")
    ProductDetailDTO getProductDetailByCartItemId(@Param("cart_item_id") Integer cart_item_id);

    boolean existsCartByCustomerId(Integer customerId);

    void deleteCartByCustomerId(Integer customerId);
}
