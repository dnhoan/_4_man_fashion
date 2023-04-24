package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.dto.SendEmailStatus;
import com.example._4_man_fashion.dto.StatisticFavorite;
import com.example._4_man_fashion.dto.StatisticIncome;
import com.example._4_man_fashion.dto.StatisticRevenue;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        @Query(value = "select od from Order od where " +
                "( (:valueSearch is null or lower(od.orderId) like lower(:valueSearch)) or " +
                "  (:valueSearch is null or lower(od.recipientName) like lower(:valueSearch)) or " +
                "  (:valueSearch is null or lower(od.recipientPhone) like lower(:valueSearch)) or " +
                "  (:valueSearch is null or lower(od.recipientEmail) like lower(:valueSearch)) or " +
                "  (:valueSearch is null or lower(od.address) like lower(:valueSearch)) ) and " +
                "  (:delivery = -1 or od.delivery = :delivery) and " +
                "  (:purchaseType = -1 or od.purchaseType = :purchaseType) and " +
//                "  (od.ctime between :s_date and :e_date) and " +
                " (:status = -1 or od.orderStatus = :status) order by od.ctime desc"
        )
        Page<Order> searchOrder(Pageable pageable, Integer status, String valueSearch,
//                                        Date s_date,
//                                        Date e_date,
//                                 LocalDateTime s_date,
//                                 LocalDateTime e_date,
                                Integer delivery, Integer purchaseType);

//        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @Query("select od from Order od where od.customerId = :customerId and ( od.orderId = :valueSearch" +
                " or (lower(od.orderId) like  '%' || lower(:valueSearch) || '%')" +
                " or (lower(od.recipientName) like  '%' || lower(:valueSearch) || '%')" +
                " or (lower(od.recipientPhone) like  '%' || lower(:valueSearch) || '%')" +
                " or (lower(od.recipientEmail) like  '%' || lower(:valueSearch) || '%') )" +
                "and (:status = -1 or od.orderStatus = :status) order by od.ctime desc")
        Page<Order> getOrderByCustomerId(Pageable pageable, Integer status, String valueSearch, Integer customerId);

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

        @Query(nativeQuery = true,value = "select cus.email as email, cus.customer_name as name, od.order_id as id from customers as cus\n" +
                "join orders as od on cus.id = od.customer_id\n" +
                "where od.id = :id")
        Optional<SendEmailStatus> getEmailCustomerByOrderId(Integer id);

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
                "                                    where   orders.purchase_type =0  \n" +
                "                                            and (orders.order_status = 5 or orders.order_status = 6) and\n" +
                "                                            orders.ctime BETWEEN :time1 and :time2\n" +
                "                                    group by  ngay ,thang,nam\n" +
                "                                    ) as o1\n" +
                "                               full join\n" +
                "                                    (select\n" +
                "                                        sum(orders.total_money) as DT_ONLINE,\n" +
                "                                    TO_CHAR(orders.ctime, 'dd/mm/yyyy') as ngay,\n" +
                "                                         TO_CHAR(orders.ctime, 'mm') as thang,\n" +
                "                                         TO_CHAR(orders.ctime, 'yyyy') as nam\n" +
                "                                   from orders                 \n" +
                "                                    where   orders.purchase_type = 1   \n" +
                "                                            and (orders.order_status = 5 or orders.order_status = 6) and\n" +
                "                                            orders.ctime BETWEEN :time1 and :time2              \n" +
                "                                    group by  ngay,thang,nam\n" +
                "                                    ) as o2    \n" +
                "                               on o1.ngay = o2.ngay order by  nam,thang,ngay  ")
                List<StatisticIncome> statisticByDate(Date time1, Date time2);

        @Query(nativeQuery = true,
                value = "SELECT o1.quantity as quantity, o1.name1 as name , o2.minPrice as minPrice, o2.maxPrice as maxPrice, o3.image as image from\n" +
                        "(select count(fp.product_id) as quantity , pds.product_name as name1 from products pds \n" +
                        "                        join favorite_product fp  on  pds.id = fp.product_id\n" +
                        "                        where fp.ctime  between :time1 and :time2 \n" +
                        "                        group by  pds.product_name) as o1\n" +
                        "join \n" +
                        "(select pds.product_name as name1, Min(pd.price) as minPrice,  MAX(pd.price) as maxPrice from products pds \n" +
                        "join product_details pd on pds.id = pd.product_id\n" +
                        "                        group by  pds.product_name) as o2 on o1.name1 = o2.name1  \n" +
                        "join \n" +
                        "(select pds.product_name as name1, pd.image as image, pd.price as price    from products pds\n" +
                        "join product_details pd on pds.id = pd.product_id) as o3\n" +
                        "on o1.name1 = o3.name1 where o3.price = o2.minPrice  order by quantity desc\n" +
                        "LIMIT 10")
        List<StatisticFavorite> statisticsByBestFavoriteProducts(Date time1, Date time2);


        @Query(nativeQuery = true, value = "select COALESCE(o1.DT_STORE, 0) as dt_store, COALESCE(o2.DT_ONLINE, 0) as dt_online, COALESCE(o1.thang, o2.thang) as thang from (\n" +
                "       select sum(orders.total_money) as DT_STORE, EXTRACT(YEAR  from orders.ctime) nam, EXTRACT(MONTH from orders.ctime) as thang \n" +
                "        from orders\n" +
                "        where orders.purchase_type = 0 and (orders.order_status = 5 or orders.order_status = 6)\n" +
                "        group by EXTRACT(YEAR  from orders.ctime), EXTRACT(MONTH from orders.ctime)\n" +
                "        having EXTRACT(YEAR  from orders.ctime) = ?1\n" +
                ") as o1 full join (\n" +
                "        select sum(orders.total_money) as DT_ONLINE,EXTRACT(YEAR  from orders.ctime) nam, EXTRACT(MONTH from orders.ctime) as thang \n" +
                "        from orders\n" +
                "        where orders.purchase_type = 1 and (orders.order_status = 5 or orders.order_status = 6) \n" +
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
                        "select count(order_status) from orders  where order_status = 5 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = 6 and ctime between ?1 and ?2\n" +
                        "union all \n" +
                        "select count(order_status) from orders  where order_status = 7 and ctime between ?1 and ?2\n"
        )
        List<Integer> statisticOrderStatus(Date s_date, Date e_date);


        @Query(nativeQuery = true,
                value = "SELECT sum(od.quantity) as quantity , pds.product_detail_name as name, pds.image as image, pds.price as price from order_details od \n" +
                        "join product_details pds on od.product_detail_id = pds.id\n" +
                        "join orders o on od.order_id = o.id\n" +
                        "where od.status_order_detail = 1 and o.ctime between ?1 and ?2\n" +
                        "group by product_detail_id , product_detail_name, pds.image , pds.price\n" +
                        "order by quantity desc\n" +
                        "LIMIT 10"

        )
        List<StatisticFavorite> statisticsByBestSellingProducts(Date time1, Date time2);

        @Query(nativeQuery = true,
                value = "select count(*) from orders od where (od.ctime between :time1 and :time2) and od.total_money = od.checkout ")
        int getOrderTotalIsCheckout(Date time1, Date time2);

        @Query(nativeQuery = true,
                value = "select count(*) from orders od where od.ctime between :time1 and :time2")
        int getOrdersTotal(Date time1, Date time2);
        @Query(nativeQuery = true,
                value = "select coalesce(o1.DT_STORE, 0) as dt_store ,coalesce(o2.DT_ONLINE,0) as dt_online from  \n" +
                        "(select sum(od.total_money) as DT_STORE, 1 as re from orders od where (od.order_status = 5 or od.order_status = 6) \n" +
                        "and od.purchase_type = 0\n" +
                        "and od.ctime between :time1 and :time2 \n" +
                        "group by re) as o1\n" +
                        "full join \n" +
                        "(select sum(od.total_money) as DT_ONLINE, 1 as re  from orders od where (od.order_status = 5 or od.order_status = 6) \n" +
                        "and od.purchase_type = 1\n" +
                        "and od.ctime between :time1 and :time2 \n" +
                        "group by re) as o2 \n" +
                        "on o1.re = o2.re ")
        StatisticRevenue getTotalRevenue(Date time1, Date time2);

        @Query(nativeQuery = true,
                value = "select o1.DT_STORE as dt_store ,o2.DT_ONLINE as dt_online from  \n" +
                        "(select sum(od.total_money) as DT_STORE, od.order_status stt from orders od where (od.order_status = 5 or od.order_status = 6) \n" +
                        "and od.purchase_type = 0\n" +
                        "and od.ctime between :time1 and :time2 \n" +
                        "group by stt) as o1\n" +
                        "full join \n" +
                        "(select sum(od.total_money) as DT_ONLINE, od.order_status stt from orders od where (od.order_status = 5 or od.order_status = 6) \n" +
                        "and od.purchase_type = 1\n" +
                        "and od.ctime between :time1 and :time2 \n" +
                        "group by stt) as o2 \n" +
                        "on o1.stt = o2.stt ")
        List<StatisticRevenue> getRevenue(Date time1, Date time2);

        @Query(nativeQuery = true,
                value = "select \tCOALESCE(o1.DT_STORE, 0) as dt_store ,\n" +
                        "COALESCE(o2.DT_ONLINE, 0) as dt_online, \n" +
                        "COALESCE(o1.SL_STORE, 0) as sl_store ,\n" +
                        "COALESCE(o2.SL_ONLINE, 0) as sl_online  \n" +
                        "from \n" +
                        "(select sum(od.total_money) as DT_STORE,\n" +
                        "count(od.purchase_type) as SL_STORE , \n" +
                        "od.order_status stt from orders od  \n" +
                        "where od.order_status = 5 \n" +
                        "and od.purchase_type = 0\n" +
                        "and od.ctime between '04-10-2023' and '04-30-2023'\n" +
                        "group by stt) as o1\n" +
                        "full join \n" +
                        "(select sum(od.total_money) as DT_ONLINE,\n" +
                        " count(od.purchase_type) as SL_ONLINE, \n" +
                        " od.order_status stt from orders od \n" +
                        " where od.order_status = 5 \n" +
                        "and od.purchase_type = 1\n" +
                        "and od.ctime between '04-10-2023' and '04-30-2023'\n" +
                        "group by stt) as o2 \n" +
                        "on o1.stt = o2.stt")
        Optional<StatisticRevenue> getRevenueAndQuantityOrder(Date time1, Date time2);
}
