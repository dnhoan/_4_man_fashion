package com.example._4_man_fashion.controllers.user;

import com.example._4_man_fashion.Service.ExchangeShopService;
import com.example._4_man_fashion.constants.Constant;
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
}

