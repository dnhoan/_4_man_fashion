package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Models;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelsRepository extends JpaRepository<Models, Integer> {

    @Modifying
    @Query("update Models model set model.status = 0 where model.id = :id")
    void deleteModels(@Param("id") Integer id);

    @Modifying
    @Query("update Models model set model.status = 1 where model.id = :id")
    void restoreModels(@Param("id") Integer id);

    @Query("select model from Models model where model.status = 1 and model.modelsName = :name order by model.id")
    List<Models> findByName(@Param("name") String name);

    @Query("select model from Models model where model.status = 0 and model.modelsName = :name order by model.id")
    List<Models> findByNameNoActive(@Param("name") String name);

    @Query("select model from Models model where model.status = 1 order by model.id")
    List<Models> getAllActive();

    @Query("select model from Models model where model.status = 0 order by model.id")
    List<Models> getAllNoActive();

    @Query("select model from Models model where (:name is null or model.modelsName like :name) and (:status = -1 or model.status = :status) order by model.ctime, model.modelsName desc")
    Page<Models> getModelsByName(Pageable pageable, Integer status, String name);

    boolean existsByModelsNameLike(String name);
}
