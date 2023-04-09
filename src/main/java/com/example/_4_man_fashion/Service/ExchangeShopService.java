package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.entities.Exchange;
import com.example._4_man_fashion.models.ReturnOrderRequestBody;

public interface ExchangeShopService {

    void returnOrderDetail(ReturnOrderRequestBody returnOrderRequestBody);
    void exchangeOrderDetail(ReturnOrderRequestBody returnOrderRequestBody);
    void rejectReturn(OrderDetailsDTO orderDetailsDTO);
    void rejectExchange(OrderDetailsDTO orderDetailsDTO);
}
