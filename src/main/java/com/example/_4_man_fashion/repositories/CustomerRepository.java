package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByAccount_Id(Integer id);

    @Query("select customer from Customer customer where (:name is null or customer.customerName like :name or customer.phoneNumber like :name) and " +
            "(:status = -1 or customer.status = :status) order by customer.ctime, customer.customerName")
    Page<Customer> getCustomerByName(Pageable pageable, Integer status, String name);


    boolean existsByEmailLike(String email);
    boolean existsByPhoneNumberLike(String phoneNumber);
}
