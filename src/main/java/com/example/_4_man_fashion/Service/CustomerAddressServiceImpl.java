package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CustomerAddressDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.CustomerAddress;
import com.example._4_man_fashion.repositories.CustomerAddressReprository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerAddressServiceImpl {

    @Autowired
    private CustomerAddressReprository customerAddressReprository;
    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<CustomerAddressDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<CustomerAddress> page = this.customerAddressReprository.getCustomerAddressByName(pageable, status, StringCommon.getLikeCondition(search));
        List<CustomerAddressDTO> customerAddressDTOS = page.stream().map(u -> this.modelMapper.map(u, CustomerAddressDTO.class)).collect(Collectors.toList());
        return new PageDTO<CustomerAddressDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                customerAddressDTOS,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }


    @Transactional
    public List<CustomerAddress> getListCustomerAddress() {
        List<CustomerAddress> customerAddressList = this.customerAddressReprository.findAll();
        return customerAddressList;
    }


    @Transactional
    public CustomerAddress create(CustomerAddressDTO customerAddressDTO) {
        if (StringCommon.isNullOrBlank(customerAddressDTO.getDetail())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        customerAddressDTO.setStatus(Constant.Status.ACTIVE);

        return (CustomerAddress) this.customerAddressReprository.save(CustomerAddress.fromDTO(customerAddressDTO));

    }

    public CustomerAddress update(CustomerAddressDTO customerAddressDTO) {
        Optional<CustomerAddress> optionalCustomerAddress = this.customerAddressReprository.findById(customerAddressDTO.getId());
        if (optionalCustomerAddress.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Địa chỉ"));

        if (StringCommon.isNullOrBlank(customerAddressDTO.getDetail()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Đia chỉ"));

        CustomerAddress customerAddress = optionalCustomerAddress.get();
        customerAddress.setWardCode(customerAddressDTO.getWardCode());
        customerAddress.setWard(customerAddressDTO.getWard());
        customerAddress.setDistrictCode(customerAddressDTO.getDistrictCode());
        customerAddress.setDistrict(customerAddressDTO.getDistrict());
        customerAddress.setProvinceCode(customerAddressDTO.getProvinceCode());
        customerAddress.setProvince(customerAddressDTO.getProvince());
        customerAddress.setDetail(customerAddressDTO.getDetail());
        customerAddress.setStatus(customerAddressDTO.getStatus());
        return (CustomerAddress) this.customerAddressReprository.save(customerAddress);
    }

    public void delete(Integer id) {
        Optional<CustomerAddress> optionalCustomerAddress = this.customerAddressReprository.findById(id);
        if (optionalCustomerAddress.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Địa chỉ"));

        CustomerAddress customerAddress = optionalCustomerAddress.get();
        customerAddress.setStatus(Constant.Status.INACTIVE);

        this.customerAddressReprository.save(customerAddress);
    }
}
