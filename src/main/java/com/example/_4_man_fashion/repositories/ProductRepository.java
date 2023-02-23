package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select product from Product product where (:name is null or product.productName like :name) and (:status = -1 or product.status = :status) order by product.ctime, product.productName")
    Page<Product> getProductByName(Pageable pageable, Integer status, String name);

    boolean existsByProductNameLike(String productName);
}
