package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.CustomerAddress;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerAddressReprository extends JpaRepository<CustomerAddress, Integer> {

    @Query("select ca from CustomerAddress ca where " +
            " (:name is null or ca.detail like :name) and (:status = -1 or ca.status = :status)  order by ca.status, ca.detail")
    Page<CustomerAddress> getCustomerAddressByName(Pageable pageable, Integer status, String name);

    List<CustomerAddress> getCustomerAddressByCustomerId(Integer customerId);
}
