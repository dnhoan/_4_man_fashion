package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.repositories.OrderDetailsRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderService orderService;

    @Override
    @Transactional
    public OrderDTO updateQuantityToOrder(String oder_id, OrderDetailsDTO orderDetailsDTO) {
        if (!this.orderRepository.existsByOrderId(oder_id)) {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Order Id"));
        }
        if (!this.orderDetailsRepository.existsById(orderDetailsDTO.getId())) {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("order_detail_id"));
        }
        this.orderDetailsRepository.updateOrderDetail(orderDetailsDTO.getQuantity(),orderDetailsDTO.getQuantityOrigin(), orderDetailsDTO.getPrice(), orderDetailsDTO.getStatusOrderDetail(), orderDetailsDTO.getId());
        this.orderRepository.updateOrderMoney(orderDetailsDTO.getOrderId());
        return this.orderService.getOrderByOrderId(oder_id);
    }

    @Override
    @Transactional
    public OrderDTO createOrderDetailToOrder(String oder_id, OrderDetailsDTO orderDetailsDTO) {
        this.orderDetailsRepository.createOrderDetails(
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getPrice(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getProductDetail().getId(),
                Constant.Status.ACTIVE);
        this.orderRepository.updateOrderMoney(orderDetailsDTO.getOrderId());
        return this.orderService.getOrderByOrderId(oder_id);
    }
}

