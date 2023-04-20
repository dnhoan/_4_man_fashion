package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CustomerAddressDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.entities.CustomerAddress;
import com.example._4_man_fashion.repositories.CustomerAddressRepository;
import com.example._4_man_fashion.repositories.CustomerRepository;
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
    private CustomerAddressRepository customerAddressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<CustomerAddressDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<CustomerAddress> page = this.customerAddressRepository.getCustomerAddressByName(pageable, status,
                StringCommon.getLikeCondition(search));
        List<CustomerAddressDTO> customerAddressDTOS = page.stream()
                .map(u -> this.modelMapper.map(u, CustomerAddressDTO.class)).collect(Collectors.toList());
        return new PageDTO<CustomerAddressDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                customerAddressDTOS,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Transactional
    public List<CustomerAddress> getListCustomerAddress(Integer customerId) {
        List<CustomerAddress> customerAddressList = this.customerAddressRepository
                .getCustomerAddressByCustomerId(customerId);
        return customerAddressList;
    }

    @Transactional
    public CustomerAddress create(Integer customerId, CustomerAddressDTO customerAddressDTO) {
        if (StringCommon.isNullOrBlank(customerAddressDTO.getDetail())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        if (!this.customerRepository.existsById(customerId)) {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Customer ID"));
        }
        CustomerAddress customerAddress = CustomerAddress.fromDTO(customerAddressDTO);
        customerAddress.setCustomer(Customer.builder().id(customerId).build());
        customerAddress.setStatus(Constant.Status.ACTIVE);

        return this.customerAddressRepository.save(customerAddress);

    }

    @Transactional
    public CustomerAddress update(CustomerAddressDTO customerAddressDTO) {
        Optional<CustomerAddress> optionalCustomerAddress = this.customerAddressRepository
                .findById(customerAddressDTO.getId());
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
        customerAddress.setRecipientEmail(customerAddressDTO.getRecipientEmail());
        customerAddress.setRecipientName(customerAddressDTO.getRecipientName());
        customerAddress.setRecipientPhone(customerAddressDTO.getRecipientPhone());
        return this.customerAddressRepository.save(customerAddress);
    }

    @Transactional
    public void delete(Integer id) {
        if (!this.customerAddressRepository.existsById(id)) {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Address ID"));
        }
        this.customerAddressRepository.deleteById(id);
    }

}
