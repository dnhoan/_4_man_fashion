package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {

    @Query("select color from Color color where (:name is null or color.colorName like :name) and (:status = -1 or color.status = :status) order by color.ctime, color.colorName, color.colorCode")
    Page<Color> getColorByName(Pageable pageable, Integer status, String name);

    @Query("select color from Color color where  color.colorCode = :code")
    Optional<Color> findByCode(String code);

    @Query(value = "select c.* \n" +
            "    from colors c \n" +
            "    join (select distinct d.color_id from product_details d where d.product_id = :product_id) a\n" +
            "        on c.id = a.color_id", nativeQuery = true)
    List<Color> getColorsByProductId(@Param("product_id") int product_id);

    @Query("select color from Color color where  color.colorName = :name")
    Color findColorByColorName(String name);

    boolean existsByColorNameLike(String name);

}
