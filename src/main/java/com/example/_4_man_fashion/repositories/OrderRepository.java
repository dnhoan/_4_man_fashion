package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select od from Order od where od.orderId = :valueSearch" +
            " or (lower(od.customerId.customerName) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(od.orderId) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(od.recipientName) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(od.recipientPhone) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(od.recipientEmail) like  '%' || lower(:valueSearch) || '%')" +
            "and (:status = -1 or od.orderStatus = :status) order by od.ctime desc")
    Page<Order> getAllOrder(Pageable pageable, Integer status, String valueSearch);


    boolean existsByOrderId(String orderId);

}
