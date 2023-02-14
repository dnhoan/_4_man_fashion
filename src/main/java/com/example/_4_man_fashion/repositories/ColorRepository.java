package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {

    @Query("select color from Color color where (:name is null or color.colorName like :name) and (:status = -1 or color.status = :status) order by color.ctime, color.colorName, color.colorCode")
    Page<Color> getColorByName(Pageable pageable, Integer status, String name);

    @Query("select color from Color color where  color.colorCode = :code")
    Optional<Color> findByCode(String code);

    @Query("select color from Color color where  color.colorName = :name")
    Color findColorByColorName(String name);

    boolean existsByColorNameLike(String name);

}
