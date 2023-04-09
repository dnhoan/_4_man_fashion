package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {

    @Modifying
    @Query(value = "update exchanges set note =:note where id = :exchangeId", nativeQuery = true)
    void updateNote(String note, @Param("exchangeId") Integer exchangeId);
}
