package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Models;
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
    @Query("update Size sz set sz.status = 0 where sz.id = :id")
    void deleteSize(@Param("id") Integer id);

    @Modifying
    @Query("update Size sz set sz.status = 1 where sz.id = :id")
    void restoreSize(@Param("id") Integer id);

    @Query("select sz from Size sz where sz.status = 1 and sz.sizeName = :name order by sz.id")
    List<Size> findByName(@Param("name") String name);

    @Query("select sz from Size sz where sz.status = 0 and sz.sizeName = :name order by sz.id")
    List<Size> findByNameNoActive(@Param("name") String name);

    @Query("select sz from Size sz where sz.status = 1 order by sz.id")
    List<Size> getAllActive();

    @Query("select sz from Size sz where sz.status = 0 order by sz.id")
    List<Size> getAllNoActive();

    @Query("select sz from Size sz where (:name is null or sz.sizeName like :name) and (:status = -1 or sz.status = :status) order by sz.ctime, sz.sizeName")
    Page<Size> getSizeByName(Pageable pageable, Integer status, String name);

    boolean existsBySizeNameLike(String name);
}
