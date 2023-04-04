package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.dto.StatisticFavorite;
import com.example._4_man_fashion.dto.StatisticIncome;
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

import java.util.Date;
import java.util.List;

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

        @Query(nativeQuery = true,value = "select  COALESCE(o1.DT_STORE, 0) as dt_store,\n" +
                "                                  COALESCE(o2.DT_ONLINE, 0) as dt_online,\n" +
                "                                  COALESCE(o1.ngay, o2.ngay) as ngay,\n" +
                "                               COALESCE(o1.thang, o2.thang) as thang,\n" +
                "                                COALESCE(o1.nam, o2.nam) as nam\n" +
                "                                from\n" +
                "                                    (select\n" +
                "                                       sum(orders.total_money) as DT_STORE,\n" +
                "                                        TO_CHAR(orders.ctime, 'dd/mm/yyyy') as ngay,\n" +
                "                                         TO_CHAR(orders.ctime, 'mm') as thang,\n" +
                "                                         TO_CHAR(orders.ctime, 'yyyy') as nam\n" +
                "                                    from orders                  \n" +
                "                                    where   orders.purchase_type =1  \n" +
                "                                            and ( orders.order_status = 1) and\n" +
                "                                            orders.ctime BETWEEN '31-01-2023' and '31-08-2024'\n" +
                "                                    group by  ngay ,thang,nam\n" +
                "                                    ) as o1\n" +
                "                               full join\n" +
                "                                    (select\n" +
                "                                        sum(orders.total_money) as DT_ONLINE,\n" +
                "                                    TO_CHAR(orders.ctime, 'dd/mm/yyyy') as ngay,\n" +
                "                                         TO_CHAR(orders.ctime, 'mm') as thang,\n" +
                "                                         TO_CHAR(orders.ctime, 'yyyy') as nam\n" +
                "                                   from orders                 \n" +
                "                                    where   orders.purchase_type = 2   \n" +
                "                                            and ( orders.order_status = 1) and\n" +
                "                                            orders.ctime BETWEEN '31-01-2023' and '31-08-2024'              \n" +
                "                                    group by  ngay,thang,nam\n" +
                "                                    ) as o2    \n" +
                "                               on o1.ngay = o2.ngay order by  nam,thang,ngay  ")
                List<StatisticIncome> statisticByDate(Date time1, Date time2);

        @Query(nativeQuery = true,
                value = "SELECT count(fp.product_id) as quantity , pds.product_name as name from favorite_product fp \n" +
                        "join products pds on fp.product_id = pds.id\n" +
                        "where fp.ctime  between :time1 and :time2\n" +
                        "group by fp.product_id , product_name \n" +
                        "order by quantity desc\n" +
                        "LIMIT 10")
        List<StatisticFavorite> statisticsByBestFavoriteProducts(Date time1, Date time2);


        @Query(nativeQuery = true, value = "select COALESCE(o1.DT_STORE, 0) as dt_store, COALESCE(o2.DT_ONLINE, 0) as dt_online, COALESCE(o1.thang, o2.thang) as thang from (\n" +
                "       select sum(orders.total_money) as DT_STORE, EXTRACT(YEAR  from orders.ctime) nam, EXTRACT(MONTH from orders.ctime) as thang \n" +
                "        from orders\n" +
                "        where orders.purchase_type = 2\n" +
                "        group by EXTRACT(YEAR  from orders.ctime), EXTRACT(MONTH from orders.ctime)\n" +
                "        having EXTRACT(YEAR  from orders.ctime) = ?1\n" +
                ") as o1 full join (\n" +
                "        select sum(orders.total_money) as DT_ONLINE,EXTRACT(YEAR  from orders.ctime) nam, EXTRACT(MONTH from orders.ctime) as thang \n" +
                "        from orders\n" +
                "        where orders.purchase_type = 1\n" +
                "        group by EXTRACT(YEAR  from orders.ctime), EXTRACT(MONTH from orders.ctime), EXTRACT(MONTH from orders.ctime)\n" +
                "        having EXTRACT(YEAR  from orders.ctime) = ?1\n" +
                ") as o2 on o1.thang = o2.thang")
        List<StatisticIncome> getStatisticIncomeByYear(Integer year);

        @Query(nativeQuery = true,
                value = "select count(order_status) from orders  where order_status = 0 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = 1 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = 2 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = 3 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = 4 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = -2 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = -1 and ctime between ?1 and ?2\n"

        )
        List<Integer> statisticOrderStatus(Date s_date, Date e_date);


        @Query(nativeQuery = true,
                value = "SELECT sum(od.quantity) as quantity , pds.product_detail_name as name, pds.price as price from order_details od \n" +
                        "join product_details pds on od.product_detail_id = pds.id\n" +
                        "join orders o on od.order_id = o.id\n" +
                        "where od.status_order_detail = 1 and o.ctime between ?1 and ?2\n" +
                        "group by product_detail_id , product_detail_name , pds.price\n" +
                        "order by quantity desc\n" +
                        "LIMIT 10"

        )
        List<StatisticFavorite> statisticsByBestSellingProducts(Date time1, Date time2);
}
