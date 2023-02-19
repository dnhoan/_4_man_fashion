package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Models;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModelsRepository extends JpaRepository<Models, Integer> {


    @Modifying
    @Query("update Models model set model.status = 1 where model.id = :id")
    void restoreModels(@Param("id") Integer id);

    @Query("select model from Models model where (:name is null or model.modelsName like :name) and (:status = -1 or model.status = :status) order by model.ctime, model.modelsName")
    Page<Models> getModelsByName(Pageable pageable, Integer status, String name);

    boolean existsByModelsNameLike(String name);
}
