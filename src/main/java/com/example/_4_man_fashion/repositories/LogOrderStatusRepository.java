package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.LogOrderStatus;
import com.example._4_man_fashion.entities.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LogOrderStatusRepository extends JpaRepository<LogOrderStatus, Integer> {

    @Query("select log from LogOrderStatus log where (:id is null or log.id =:id) order by log.id")
    List<LogOrderStatus> getAllById(Integer id);
}
