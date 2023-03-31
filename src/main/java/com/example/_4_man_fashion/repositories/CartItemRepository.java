package com.example._4_man_fashion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example._4_man_fashion.entities.CartItem;
import com.example._4_man_fashion.entities.ProductDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Query("select pd from ProductDetail pd where pd.id = :pd_id")
    ProductDetail getProductDetailById(@Param("pd_id") Integer pd_id);


    @Modifying
    @Query(value = "delete from carts_items using carts where carts_items.cart_id = carts.id and carts.customer_id = :customer_id", nativeQuery = true)
    void deleteCartItemsByCustomerId(Integer customer_id);
    Optional<CartItem> getCartItemByProductDetailIdAndCartId(Integer productDetailId, Integer cartId);
}
