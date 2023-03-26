package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.CustomerDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Customer;

public interface CustomerService {
    PageDTO<CustomerDTO> getAll(int offset, int limit, Integer status, String search);
    Customer create(CustomerDTO customerDTO);
    CustomerDTO getCustomerById(Integer id);

    Customer update(CustomerDTO customerDTO);

    void delete(Integer id);
}
