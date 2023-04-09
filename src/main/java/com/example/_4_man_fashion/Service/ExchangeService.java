package com.example._4_man_fashion.Service;


import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.models.ReturnOrderRequestBody;

public interface ExchangeService {

    void rejectReturn(OrderDetailsDTO orderDetailsDTO);
    void rejectExchange(OrderDetailsDTO orderDetailsDTO);
    void confirmReturn(OrderDetailsDTO orderDetailsDTO);
    void confirmExchange(OrderDetailsDTO orderDetailsDTO);
    void returnOrderDetail(ReturnOrderRequestBody returnOrderRequestBody);
    void exchangeOrderDetail(ReturnOrderRequestBody returnOrderRequestBody);
}
