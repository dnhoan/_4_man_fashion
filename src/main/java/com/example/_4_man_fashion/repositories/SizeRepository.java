package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SizeRepository extends JpaRepository<Size, Integer> {

    @Modifying
    @Query("update Size sz set sz.status = 1 where sz.id = :id")
    void restoreSize(@Param("id") Integer id);

    @Query(value = """
            select s.*\s
                from sizes s\s
                join (select distinct d.size_id from product_details d where d.product_id = :product_id) a
                    on s.id = a.size_id""", nativeQuery = true)
    List<Size> getSizesByProductId(@Param("product_id") int product_id);

    @Query("select sz from Size sz where (:name is null or sz.sizeName like :name) and (:status = -1 or sz.status = :status) order by sz.ctime desc, sz.sizeName")
    Page<Size> getSizeByName(Pageable pageable, Integer status, String name);


    boolean existsBySizeNameLike(String name);
}
