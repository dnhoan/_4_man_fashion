package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.entities.Exchange;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.entities.OrderDetails;
import com.example._4_man_fashion.models.ReturnOrderRequestBody;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import com.example._4_man_fashion.repositories.OrderDetailsRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExchangeShopServiceImpl implements ExchangeShopService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderRepository orderRepository;

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
        Order order = new Order();
        order.setId(returnOrderRequestBody.getOrderId());
        orderDetails.setExchange(exchange);
        orderDetails.setOrder(order);
        // create new order_detail
        this.orderDetailsRepository.save(orderDetails);
        this.orderRepository.updateOrderMoney(returnOrderRequestBody.getOrderId());
    }
}
