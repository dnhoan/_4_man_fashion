package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.CustomerAddressDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.CustomerAddress;

public interface CustomerAddressService {

    PageDTO<CustomerAddressDTO> getAll(int offset, int limit, Integer status, String search);

    CustomerAddress create(CustomerAddressDTO customerAddressDTO);

    CustomerAddress update(CustomerAddressDTO customerAddressDTO);

    void delete(Integer id);
}
