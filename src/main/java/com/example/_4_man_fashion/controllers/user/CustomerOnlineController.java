package com.example._4_man_fashion.controllers.user;

import com.example._4_man_fashion.Service.CustomerServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CustomerDTO;
import com.example._4_man_fashion.dto.CustomerOnlineDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.entities.Cart;
import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.USER)
public class CustomerOnlineController {

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



    @PutMapping("/customer/update")
    public ResponseEntity<ApiResponse<JwtResponse>> update(@Valid @RequestBody CustomerOnlineDTO dto) {
        JwtResponse jwtResponse = customerService.updateOnline(dto);
        return ResponseEntity.ok(ApiResponse.success(jwtResponse));
    }

    @DeleteMapping("/customer/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
