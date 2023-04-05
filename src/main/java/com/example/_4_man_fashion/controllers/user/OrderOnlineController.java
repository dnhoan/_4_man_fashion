package com.example._4_man_fashion.controllers.user;

import com.example._4_man_fashion.Service.OrderService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.LogOrderStatusDTO;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.USER)
@CrossOrigin("*")
public class OrderOnlineController {

    @Autowired
    private OrderService orderService;

    @GetMapping("orders/{customerId}")
    public ResponseEntity<ApiResponse<PageDTO<OrderDTO>>> getOrdersByCustomerId(
            @PathVariable Integer customerId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "-1") Integer status,
            @RequestParam(defaultValue = "") String search) {
        PageDTO<OrderDTO> result = orderService.getOrderByCustomerId(customerId, offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("order/getList")
    public ResponseEntity<ApiResponse<List<Order>>> getList() {
        List<Order> lstOrder = this.orderService.getListOrder();
        return ResponseEntity.ok(ApiResponse.success(lstOrder));
    }
    @PutMapping("order/updateOrderStatus")
    public ResponseEntity<ApiResponse<Boolean>> updateOrderStatus(@RequestBody UpdateOrderStatus statusUpdate) {
        this.orderService.updateOrderShopStatus(statusUpdate);
        return ResponseEntity.ok(ApiResponse.success(true));
    }
    @PostMapping("order/create")
    public ResponseEntity<ApiResponse<Order>> create(@Valid @RequestBody OrderDTO dto) {
        Order order = orderService.createOrderOnline(dto);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @PutMapping("order/update")
    public ResponseEntity<ApiResponse<OrderDTO>> update(@Valid @RequestBody OrderDTO dto) {
        OrderDTO order = orderService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @DeleteMapping("order/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        orderService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
