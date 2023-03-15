package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.FavoriteProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {


    @Query("select f from FavoriteProduct f where f.customer.id = :customer_id")
    Page<FavoriteProduct> getFavoriteProductByCustomerId(Pageable pageable, Integer customer_id);;
}
