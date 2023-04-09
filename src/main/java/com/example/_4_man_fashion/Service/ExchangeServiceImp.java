package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.repositories.ExchangeRepository;
import com.example._4_man_fashion.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExchangeServiceImp implements  ExchangeService{

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Override
    @Transactional
    public void rejectReturn(OrderDetailsDTO orderDetailsDTO) {
        this.orderDetailsRepository.updateStatusOrderDetail(Constant.OrderDetailStatus.REJECT_RETURN, orderDetailsDTO.getId());
        this.exchangeRepository.updateNote(orderDetailsDTO.getExchange().getNote(), orderDetailsDTO.getExchange().getId());
    }
    @Override
    @Transactional
    public void rejectExchange(OrderDetailsDTO orderDetailsDTO) {
        this.orderDetailsRepository.updateStatusOrderDetail(Constant.OrderDetailStatus.REJECT_EXCHANGE, orderDetailsDTO.getId());
        this.exchangeRepository.updateNote(orderDetailsDTO.getExchange().getNote(), orderDetailsDTO.getExchange().getId());
    }

    @Override
    @Transactional
    public void confirmReturn(OrderDetailsDTO orderDetailsDTO) {
//        this.orderDetailsRepository.updateStatusOrderDetail(Constant.OrderDetailStatus.RETURN, orderDetailsDTO.getId());
        this.exchangeRepository.updateNote(orderDetailsDTO.getExchange().getNote(), orderDetailsDTO.getExchange().getId());
        this.orderDetailsRepository.updateQuantityOrderDetail(Math.abs(orderDetailsDTO.getQuantity()), orderDetailsDTO.getExchange().getOrderDetailIdOrigin());
        this.orderDetailsRepository.updateOrderDetail(orderDetailsDTO.getQuantity(), orderDetailsDTO.getQuantityOrigin(), orderDetailsDTO.getPrice(), Constant.OrderDetailStatus.RETURN, orderDetailsDTO.getId());
    }
    @Override
    @Transactional
    public void confirmExchange(OrderDetailsDTO orderDetailsDTO) {
        this.exchangeRepository.updateNote(orderDetailsDTO.getExchange().getNote(), orderDetailsDTO.getExchange().getId());
        this.orderDetailsRepository.updateQuantityOrderDetail(Math.abs(orderDetailsDTO.getQuantity()), orderDetailsDTO.getExchange().getOrderDetailIdOrigin());
        this.orderDetailsRepository.updateOrderDetail(orderDetailsDTO.getQuantity(), orderDetailsDTO.getQuantityOrigin(), orderDetailsDTO.getPrice(), Constant.OrderDetailStatus.EXCHANGE, orderDetailsDTO.getId());
    }
}
