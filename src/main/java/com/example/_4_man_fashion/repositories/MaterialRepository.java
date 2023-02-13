package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("select mate from Material mate where mate.status = 1 order by mate.id")
    Page<Material> getAllById(Pageable pageable);

    @Query("select mate from Material mate where  mate.materialName = :name")
    Material findMaterialByMaterialName(String name);

    @Query("select mate from Material mate where  mate.id = :id")
    Material findMaterialById(Integer id);
}
