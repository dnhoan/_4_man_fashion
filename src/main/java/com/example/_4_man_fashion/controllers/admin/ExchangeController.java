package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.ExchangeService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.models.ReturnOrderRequestBody;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @PutMapping("orderExchange/rejectReturn")
    public ResponseEntity<ApiResponse<Boolean>> rejectReturn(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        this.exchangeService.rejectReturn(orderDetailsDTO);

        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @PutMapping("orderExchange/rejectExchange")
    public ResponseEntity<ApiResponse<Boolean>> rejectExchange(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        this.exchangeService.rejectExchange(orderDetailsDTO);

        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @PutMapping("orderExchange/confirmReturn")
    public ResponseEntity<ApiResponse<Boolean>> confirmReturn(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        this.exchangeService.confirmReturn(orderDetailsDTO);

        return ResponseEntity.ok(ApiResponse.success(true));
    }
    @PutMapping("orderExchange/confirmExchange")
    public ResponseEntity<ApiResponse<Boolean>> confirmExchange(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        this.exchangeService.confirmExchange(orderDetailsDTO);

        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @PostMapping("order/return")
    public ResponseEntity<ApiResponse<Boolean>> returnOrderDetail(
            @RequestBody ReturnOrderRequestBody returnOrderRequestBody
    ) {
        this.exchangeService.returnOrderDetail(returnOrderRequestBody);
        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @PostMapping("order/exchange")
    public ResponseEntity<ApiResponse<Boolean>> exchangeOrderDetail(
            @RequestBody ReturnOrderRequestBody returnOrderRequestBody
    ) {
        this.exchangeService.exchangeOrderDetail(returnOrderRequestBody);
        return ResponseEntity.ok(ApiResponse.success(true));
    }
}
