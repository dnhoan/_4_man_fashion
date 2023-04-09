package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDetailsDTO;
import com.example._4_man_fashion.entities.Exchange;
import com.example._4_man_fashion.entities.ExchangeImages;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.entities.OrderDetails;
import com.example._4_man_fashion.models.ReturnOrderRequestBody;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import com.example._4_man_fashion.repositories.ExchangeRepository;
import com.example._4_man_fashion.repositories.OrderDetailsRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeShopServiceImpl implements ExchangeShopService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Override
    @Transactional
    public void returnOrderDetail(ReturnOrderRequestBody returnOrderRequestBody) {
        // update status order to exchange
        if(returnOrderRequestBody.getStatusOrder().equals(Constant.OrderStatus.COMPLETE)) {
            UpdateOrderStatus updateOrderStatus = UpdateOrderStatus.builder()
                    .newStatus(Constant.OrderStatus.EXCHANGE)
                    .orderId(returnOrderRequestBody.getOrderId())
                    .note(returnOrderRequestBody.getOrderDetails().getExchange().getReason())
                    .build();
            this.orderService.updateOrderStatus(updateOrderStatus);
        }

        OrderDetails orderDetails = returnOrderRequestBody.getOrderDetails();

        Exchange exchange = returnOrderRequestBody.getOrderDetails().getExchange();
        List<ExchangeImages> exchangeImages =  exchange.getExchangeImages().stream().map(img -> {
            img.setExchange(exchange);
            return img;
        }).collect(Collectors.toList());
        exchange.setExchangeImages(exchangeImages);
        Order order = new Order();
        order.setId(returnOrderRequestBody.getOrderId());
        orderDetails.setExchange(exchange);
        orderDetails.setOrder(order);
        orderDetails.setStatusOrderDetail(Constant.OrderDetailStatus.RETURN_PENDING);
        // create new order_detail
//        this.orderDetailsRepository.updateQuantityOrderDetail(Math.abs(orderDetails.getQuantity()),  orderDetails.getId());
        this.orderDetailsRepository.save(orderDetails);
//        this.orderRepository.updateOrderMoney(returnOrderRequestBody.getOrderId());
    }

    @Override
    @Transactional
    public void exchangeOrderDetail(ReturnOrderRequestBody returnOrderRequestBody) {
        // update status order to exchange
        if(returnOrderRequestBody.getStatusOrder().equals(Constant.OrderStatus.COMPLETE)) {
            UpdateOrderStatus updateOrderStatus = UpdateOrderStatus.builder()
                    .newStatus(Constant.OrderStatus.EXCHANGE)
                    .orderId(returnOrderRequestBody.getOrderId())
                    .note(returnOrderRequestBody.getOrderDetails().getExchange().getReason())
                    .build();
            this.orderService.updateOrderStatus(updateOrderStatus);
        }

        OrderDetails orderDetails = returnOrderRequestBody.getOrderDetails();

        Exchange exchange = returnOrderRequestBody.getOrderDetails().getExchange();
        List<ExchangeImages> exchangeImages =  exchange.getExchangeImages().stream().map(img -> {
            img.setExchange(exchange);
            return img;
        }).collect(Collectors.toList());
        exchange.setExchangeImages(exchangeImages);
        Order order = new Order();
        order.setId(returnOrderRequestBody.getOrderId());
        orderDetails.setExchange(exchange);
        orderDetails.setOrder(order);
        orderDetails.setStatusOrderDetail(Constant.OrderDetailStatus.EXCHANGE_PENDING);
        // create new order_detail
//        this.orderDetailsRepository.updateQuantityOrderDetail(Math.abs(orderDetails.getQuantity()),  orderDetails.getId());
        this.orderDetailsRepository.save(orderDetails);
//        this.orderRepository.updateOrderMoney(returnOrderRequestBody.getOrderId());
    }

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
}
