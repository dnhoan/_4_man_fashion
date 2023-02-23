package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

//    @Query("select p from ProductDetail p where p.productId = :product_id and p.status =:status")
//    List<ProductDetail> getProductDetailsByProductId(
//            @Param("product_id") Integer product_id ,
//            @Param("status") Integer status
//    );

}
