package com.example._4_man_fashion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example._4_man_fashion.entities.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

    @Modifying
    @Query(value = "INSERT INTO order_details(\n" +
                     "\torder_id, price, quantity, product_detail_id, status_order_detail, quantity_origin)\n" +
            "\tVALUES (:order_id, :price, :quantity, :product_detail_id, :status_order_detail, :quantityOrigin);", nativeQuery = true)
    void createOrderDetails(Integer order_id, Float price, Integer quantity, Integer quantityOrigin, Integer product_detail_id, Integer status_order_detail);

    @Modifying
    @Query(value = "update order_details set quantity = :quantity, quantity_origin = :quantityOrigin, price = :price, status_order_detail = :status_order_detail  where id = :orderDetailId", nativeQuery = true)
    void updateOrderDetail(Integer quantity,Integer quantityOrigin, float price,Integer status_order_detail, Integer orderDetailId);

    @Modifying
    @Query(value = "update order_details set quantity = (order_details.quantity - :quantity) where id = :orderDetailId", nativeQuery = true)
    void updateQuantityOrderDetail(Integer quantity, Integer orderDetailId);

    @Modifying
    @Query(value = "update order_details set status_order_detail = :statusOrderDetail where id = :orderDetailId", nativeQuery = true)
    void updateStatusOrderDetail(Integer statusOrderDetail, Integer orderDetailId);

}
