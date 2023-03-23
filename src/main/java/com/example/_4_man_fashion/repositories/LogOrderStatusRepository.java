package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.LogOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogOrderStatusRepository extends JpaRepository<LogOrderStatus, Integer> {
}
