package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.FavoriteProduct;
import com.example._4_man_fashion.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {


    @Query("select f.product from FavoriteProduct f where f.customer.id = :customer_id")
    Page<Product> getFavoriteProductByCustomerId(Pageable pageable, Integer customer_id);;

    @Query("select f.product from FavoriteProduct f where (:productName is null or f.product.productName like :productName)  order by f.ctime, f.product.productName")
    Page<Product> getFavoriteProductByProductName(Pageable pageable, String productName);

    boolean existsFavoriteProductByProductId(Integer productId);
}
