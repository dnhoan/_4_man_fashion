package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.dto.FavoriteProductList;
import com.example._4_man_fashion.dto.StatisticFavorite;
import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.FavoriteProduct;
import com.example._4_man_fashion.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {


    @Query("select f.product from FavoriteProduct f where f.customer.id = :customer_id")
    Page<Product> getFavoriteProductByCustomerId(Pageable pageable, Integer customer_id);;

    @Query("select f.product from FavoriteProduct f where (:productName is null or f.product.productName like :productName)  order by f.ctime, f.product.productName")
    Page<Product> getFavoriteProductByProductName(Pageable pageable, String productName);

    boolean existsFavoriteProductByProductIdAndCustomerId(Integer productId, Integer customerId);

    @Query(nativeQuery = true,
            value = "SELECT  o1.name1 as name,o1.time, o2.minPrice as minPrice, o2.maxPrice as maxPrice, o3.image from\n" +
                    "(select pds.product_name as name1 , fp.ctime as time  from products pds \n" +
                    "                        join favorite_product fp  on  pds.id = fp.product_id\n" +
                    "                        where fp.customer_id = :customer_id\n" +
                    "                        group by  pds.product_name , time) as o1\n" +
                    "join " +
                    "(select pds.product_name as name1, Min(pd.price) as minPrice,  MAX(pd.price) as maxPrice from products pds \n" +
                    "join product_details pd on pds.id = pd.product_id\n" +
                    "                        group by  pds.product_name) as o2 on o1.name1 = o2.name1  \n" +
                    "join \n" +
                    "(select pds.product_name as name1, pd.image as image, pd.price as price    from products pds\n" +
                    "join product_details pd on pds.id = pd.product_id) as o3\n" +
                    "on o1.name1 = o3.name1 where o3.price = o2.minPrice order by o1.time desc  ")
    List<FavoriteProductList> getListFavoriveProductByCustomerId( Integer customer_id);
}
