package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.CustomerAddressServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CustomerAddressDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.CustomerAddress;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.USER)
public class CustomerAddressController {
    @Autowired
    public CustomerAddressServiceImpl customerAddressService;

    @GetMapping("customerAddress/getAll")
    public ResponseEntity<ApiResponse<PageDTO<CustomerAddressDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "1") Integer status,
            @RequestParam(defaultValue = "") String search) {
        PageDTO<CustomerAddressDTO> result = customerAddressService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("customerAddress/getList/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerAddress>>> getList(@PathVariable Integer customerId) {
        List<CustomerAddress> listAddress = this.customerAddressService.getListCustomerAddress(customerId);
        return ResponseEntity.ok(ApiResponse.success(listAddress));
    }

    @PostMapping("/customerAddress/create/{customerId}")
    public ResponseEntity<ApiResponse<CustomerAddress>> create(@PathVariable Integer customerId,
            @Valid @RequestBody CustomerAddressDTO dto) {
        CustomerAddress customerAddress = customerAddressService.create(customerId, dto);
        return ResponseEntity.ok(ApiResponse.success(customerAddress));
    }

    @PutMapping("/customerAddress/update")
    public ResponseEntity<ApiResponse<CustomerAddress>> update(@Valid @RequestBody CustomerAddressDTO dto) {
        CustomerAddress customerAddress = customerAddressService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(customerAddress));
    }

    @DeleteMapping("/customerAddress/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        customerAddressService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
