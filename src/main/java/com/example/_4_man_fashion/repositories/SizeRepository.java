package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SizeRepository extends JpaRepository<Size, Integer> {

    @Modifying
    @Query("update Size sz set sz.status = 1 where sz.id = :id")
    void restoreSize(@Param("id") Integer id);

    @Query("select sz from Size sz where (:name is null or sz.sizeName like :name) and (:status = -1 or sz.status = :status) order by sz.ctime desc, sz.sizeName")
    Page<Size> getSizeByName(Pageable pageable, Integer status, String name);

    boolean existsBySizeNameLike(String name);
}
