package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.CustomerServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.entities.Cart;
import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("customer/getAll")
    public ResponseEntity<ApiResponse<PageDTO<CustomerDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "1") Integer status,
                                                                 @RequestParam(defaultValue = "") String search) {
        PageDTO<CustomerDTO> pageDTO = this.customerService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(pageDTO));
    }

    @GetMapping("customer/getList")
    public ResponseEntity<ApiResponse<List<Customer>>> getList() {
        List<Customer> lstCustomer = this.customerService.getlistCustomer();
        return ResponseEntity.ok(ApiResponse.success(lstCustomer));
    }


//    @PostMapping("/customer/create")
//    public ResponseEntity<ApiResponse<Customer>> create(@Valid @RequestBody CustomerDTO dto) {
//        Customer customer = customerService.create(dto);
//        return ResponseEntity.ok(ApiResponse.success(customer));
//    }

    @PostMapping("/customer/create")
    public ResponseEntity<ApiResponse<Cart>> create(@Valid @RequestBody CustomerDTO dto) {
        Cart customer = customerService.createCustomer(dto);
        return ResponseEntity.ok(ApiResponse.success(customer));
    }

    @PutMapping("/customer/update")
    public ResponseEntity<ApiResponse<Customer>> update(@Valid @RequestBody CustomerDTO dto) {
        Customer customer = customerService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(customer));
    }

    @DeleteMapping("/customer/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
