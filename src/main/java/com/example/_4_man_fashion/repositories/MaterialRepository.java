package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("select mate from Material mate where (:name is null or mate.materialName like :name) and (:status = -1 or mate.status = :status) order by mate.ctime, mate.materialName")
    Page<Material> getMaterialByName(Pageable pageable, Integer status, String name);

    @Query("select mate from Material mate where  mate.materialName = :name")
    Optional<Material> findByMaterialName(String name);


    boolean existsByMaterialNameLike(String name);
}
