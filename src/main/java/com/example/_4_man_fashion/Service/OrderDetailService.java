package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.OrderDetailsDTO;

public interface OrderDetailService {

    OrderDTO updateQuantityToOrder(String oder_id, OrderDetailsDTO orderDetails);

    OrderDTO createOrderDetailToOrder(String oder_id, OrderDetailsDTO orderDetails);
}
