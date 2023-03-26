package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.OrderDetailService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("orderDetail/{oder_id}")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrderDetailToOrder(@PathVariable String oder_id, @RequestBody OrderDetailsDTO orderDetailsDTO) {
        OrderDTO o  =  this.orderDetailService.createOrderDetailToOrder(oder_id, orderDetailsDTO);
        return ResponseEntity.ok(ApiResponse.success(o));
    }
    @PutMapping("orderDetail/{oder_id}")
    public ResponseEntity<ApiResponse<OrderDTO>> updateQuantityToOrder(@PathVariable String oder_id, @RequestBody OrderDetailsDTO orderDetailsDTO) {
        OrderDTO o =  this.orderDetailService.updateQuantityToOrder(oder_id, orderDetailsDTO);
        return ResponseEntity.ok(ApiResponse.success(o));
    }
}
