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
                     "\torder_id, price, quantity, product_detail_id, status_order_detail)\n" +
            "\tVALUES (:order_id, :price, :quantity, :product_detail_id, :status_order_detail);", nativeQuery = true)
    void createOrderDetails(Integer order_id, Float price, Integer quantity, Integer product_detail_id, Integer status_order_detail);

    @Modifying
    @Query(value = "update order_details set quantity = :quantity, price = :price, status_order_detail = :status_order_detail  where id = :orderDetailId", nativeQuery = true)
    void updateOrderDetail(Integer quantity, float price,Integer status_order_detail, Integer orderDetailId);
}
