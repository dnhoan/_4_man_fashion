package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select product from Product product where " +
            "((:name is null or lower(product.categoryName) like lower(:name)) or " +
            "(:name is null or lower(product.materialName) like lower(:name)) or " +
            "(:name is null or lower(product.modelName) like lower(:name)) or " +
            "(:name is null or lower(product.productName) like lower(:name))) and " +
            "(:status = -1 or product.status = :status) order by product.ctime desc, product.productName")
    Page<Product> getProductByName(Pageable pageable, Integer status, String name);

    @Query("select distinct product from Product product join fetch product.productDetails pd where" +
            "(:name is null or lower(product.productName) like lower(:name)) and " +
            "(COALESCE(:models) is null or product.model.id IN (:models)) and " +
            "(COALESCE(:materials) is null or product.material.id IN (:materials)) and " +
            "(COALESCE(:categories) is null or product.category.id IN (:categories)) and " +
            "(product.id in " +
                "(select pd2.product.id from product.productDetails pd2 where " +
                    "(:name is null or lower(pd2.prod   uctDetailName) like lower(:name)) and " +
                    "(COALESCE(:colors) is null or pd2.color.id IN (:colors)) and " +
                    "(COALESCE(:sizes)  is null or pd2.size.id IN (:sizes)) and " +
                    "pd2.price >= :priceMin and pd2.price <= :priceMax and pd2.status = 1" +
                ")" +
            ") and " +
            "(:status = -1 or product.status = :status) order by product.ctime desc, product.productName")
    List<Product> searchProducts(Integer status,
                                 String name,
                                 List<Integer> categories,
                                 List<Integer> materials,
                                 List<Integer> models,
                                 List<Integer> colors,
                                 List<Integer> sizes,
                                 float priceMin,
                                 float priceMax
    );

    @Query("select product from Product product where (:id is null or product.id like :id) and (:status = -1 or product.status = :status) order by product.ctime, product.id")
    Product getProductById(Integer id);

    boolean existsByProductNameLike(String productName);

    @Modifying
    @Query("update Product p set p.status = :status where p.id = :id")
    void updateProductStatus(@Param("id") Integer id, @Param("status") Integer status);
}
