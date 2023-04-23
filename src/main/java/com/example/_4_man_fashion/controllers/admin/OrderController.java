package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.OrderService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.LogOrderStatusDTO;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.models.SearchOrder;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("order/getAll")
    public ResponseEntity<ApiResponse<PageDTO<OrderDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "1") Integer status,
                                                                 @RequestParam(defaultValue = "") String search) {
        PageDTO<OrderDTO> result = orderService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("order/search")
    public ResponseEntity<ApiResponse<PageDTO<OrderDTO>>> searchOrder(@RequestBody SearchOrder searchOrder) {
        PageDTO<OrderDTO> result = orderService.searchOrder(searchOrder);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("order/getList")
    public ResponseEntity<ApiResponse<List<Order>>> getList() {
        List<Order> lstOrder = this.orderService.getListOrder();
        return ResponseEntity.ok(ApiResponse.success(lstOrder));
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderByOrderId(@PathVariable String orderId) {
        OrderDTO o = this.orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.success(o));
    }


    @PostMapping("order/create")
    public ResponseEntity<ApiResponse<Order>> create(@Valid @RequestBody OrderDTO dto) {
        Order order = orderService.create(dto);
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

    @PutMapping("order/updateOrderStatus")
    public ResponseEntity<ApiResponse<LogOrderStatusDTO>> updateOrderStatus(@RequestBody UpdateOrderStatus statusUpdate) {
        LogOrderStatusDTO logOrderStatusDTO = orderService.updateOrderStatus(statusUpdate);
        return ResponseEntity.ok(ApiResponse.success(logOrderStatusDTO));
    }
}
