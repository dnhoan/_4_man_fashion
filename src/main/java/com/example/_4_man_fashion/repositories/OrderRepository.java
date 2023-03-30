package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
        @Query("select od from Order od where od.orderId = :valueSearch" +
                        " or (lower(od.orderId) like  '%' || lower(:valueSearch) || '%')" +
                        " or (lower(od.recipientName) like  '%' || lower(:valueSearch) || '%')" +
                        " or (lower(od.recipientPhone) like  '%' || lower(:valueSearch) || '%')" +
                        " or (lower(od.recipientEmail) like  '%' || lower(:valueSearch) || '%')" +
                        "and (:status = -1 or od.orderStatus = :status) order by od.ctime desc")
        Page<Order> getAllOrder(Pageable pageable, Integer status, String valueSearch);

        @Modifying
        @Query("update Order o set o.orderStatus =:#{#updateOrderStatus.newStatus},  o.cancelNote =:#{#updateOrderStatus.note} where o.id =:#{#updateOrderStatus.orderId}")
        void updateOrderStatus(
                        @Param("updateOrderStatus") UpdateOrderStatus updateOrderStatus);

        @Modifying
        @Query(value = "CALL updateOrderMoney(:idOrder);", nativeQuery = true)
        void updateOrderMoney(Integer idOrder);

        @Query("select max(o.id) from Order o")
        int getMaxOrderId();

        boolean existsByOrderId(String orderId);

        Optional<Order> getOrderByOrderId(String orderId);
}
