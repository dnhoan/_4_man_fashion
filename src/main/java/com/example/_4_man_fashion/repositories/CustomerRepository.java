package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByAccount_Id(Integer id);
}
