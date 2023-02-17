package com.example._4_man_fashion.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example._4_man_fashion.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select cate from Category cate where (:name is null or cate.categoryName like :name) and " +
            "(:status = -1 or cate.status = :status) order by cate.categoryName")
    Page<Category> getCategoryByName(Pageable pageable, Integer status, String name);

    @Query("select cate from Category cate where cate.categoryName = :name")
    Category findCategoryByName(String name);

    boolean existsByCategoryName(String name);

}
