package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select product from Product product where (:name is null or product.productName like :name) and (:status = -1 or product.status = :status) order by product.ctime desc, product.productName")
    Page<Product> getProductByName(Pageable pageable, Integer status, String name);

    @Query("select product from Product product where (:id is null or product.id like :id) and (:status = -1 or product.status = :status) order by product.ctime, product.id")
    Product getProductById(Integer id);

    boolean existsByProductNameLike(String productName);

    @Modifying
    @Query("update Product p set p.status = :status where p.id = :id")
    void updateProductStatus(@Param("id") Integer id, @Param("status") Integer status);
}
