package com.example._4_man_fashion.controllers.user;

import com.example._4_man_fashion.Service.ExchangeShopService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.entities.Exchange;
import com.example._4_man_fashion.models.ReturnOrderRequestBody;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.Api.Path.USER)
public class ExchangeShopController {

    @Autowired
    private ExchangeShopService exchangeShopService;

    @PostMapping("order/return")
    public ResponseEntity<ApiResponse<Boolean>> returnOrderDetail(
            @RequestBody ReturnOrderRequestBody returnOrderRequestBody
            ) {
        this.exchangeShopService.returnOrderDetail(returnOrderRequestBody);
        return ResponseEntity.ok(ApiResponse.success(true));
    }
    @PostMapping("order/exchange")
    public ResponseEntity<ApiResponse<Boolean>> exchangeOrderDetail(
            @RequestBody ReturnOrderRequestBody returnOrderRequestBody
    ) {
        this.exchangeShopService.exchangeOrderDetail(returnOrderRequestBody);
        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @PutMapping("order/rejectReturn")
    public ResponseEntity<ApiResponse<Boolean>> rejectReturn(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        this.exchangeShopService.rejectReturn(orderDetailsDTO);

        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @PutMapping("order/rejectExchange")
    public ResponseEntity<ApiResponse<Boolean>> rejectExchange(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        this.exchangeShopService.rejectExchange(orderDetailsDTO);

        return ResponseEntity.ok(ApiResponse.success(true));
    }
}

