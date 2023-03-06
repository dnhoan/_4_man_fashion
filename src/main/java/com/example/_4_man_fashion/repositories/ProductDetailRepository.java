package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    @Query("select pd from ProductDetail pd where (:product_id is null or pd.product.productId = :product_id) and (:status = -1 or pd.product.status = :status) ")
    List<ProductDetail> getProductDetailsByProductId(@Param("product_id") Integer product_id,@Param("status") Integer status);
    @Modifying
    @Query("update ProductDetail p set p.status = :status where p.product.productId = :product_id and ((p.status = 1 and :status = 0) or (p.status = 0 and :status = 1) ) ")
    void updateProductDetailStatus(@Param("product_id") Integer product_id, @Param("status") Integer status); // Chỉ cập nhật sản phẩm chi tiết có trạng thái 0 và 1

//    boolean existsByColorAndSizeAndId(String colorName, String sizeName, Integer productId);
//@Query("select p from Product p join ProductDetail pd on p.id = pd.productId where (:productId is null or pd.productId = :productId) and (:status = -1 or pd.product.status = :status) ")
//    List<Product> findByProductId(Integer productId);
}
