package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.LogOrderStatusDTO;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.models.UpdateOrderStatus;

import java.util.List;

public interface OrderService {

    PageDTO<OrderDTO> getAll(int offset, int limit, Integer status, String search);
    PageDTO<OrderDTO> getOrderByCustomerId(int customerId, int offset, int limit, Integer status, String search);

    List<Order> getListOrder();
    OrderDTO getOrderByOrderId(String orderId);
    LogOrderStatusDTO updateOrderStatus(UpdateOrderStatus updateOrderStatus);
    void updateOrderShopStatus(UpdateOrderStatus updateOrderStatus);
    Order create(OrderDTO orderDTO);
    Order createOrderOnline(OrderDTO orderDTO);
    OrderDTO update(OrderDTO orderDTO);

    void delete(Integer id);

    boolean restore(Integer modelsId);
}
