package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.LogOrderStatus;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.entities.OrderDetails;
import com.example._4_man_fashion.entities.ProductDetail;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import com.example._4_man_fashion.repositories.LogOrderStatusRepository;
import com.example._4_man_fashion.repositories.OrderDetailsRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private LogOrderStatusRepository logOrderStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageDTO<OrderDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Order> page = this.orderRepository.getAllOrder(pageable, status, StringCommon.getLikeCondition(search));
        List<OrderDTO> orderDTOList = page.stream().map(this::mapOrderToOrderDTO).collect(Collectors.toList());
        return new PageDTO<OrderDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                orderDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Transactional
    public List<Order> getListOrder() {
        return null;
    }

    @Transactional
    public void updateOrderStatus(UpdateOrderStatus updateOrderStatus) {
        Optional<Order> order = this.orderRepository.findById(updateOrderStatus.getId());
        if (order.isPresent()) {
            this.orderRepository.updateOrderStatus(updateOrderStatus);
        }
        LogOrderStatus logOrderStatus = new LogOrderStatus();
        logOrderStatus.setOrder(order.get());
        logOrderStatus.setTimes(LocalDateTime.now());
        logOrderStatus.setUser_change("Admin");
        logOrderStatus.setNote(updateOrderStatus.getNote());
        logOrderStatus.setCurrentStatus(order.get().getOrderStatus());
        logOrderStatus.setNewStatus(updateOrderStatus.getNewStatus());
        logOrderStatus.setAccounts(null);
        logOrderStatus.setProductDetails(null);
        this.logOrderStatusRepository.save(logOrderStatus);

    }

    @Transactional
    public Order create(OrderDTO orderDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        orderDTO.setOrderId(timestamp.getTime() + "");
        orderDTO.setCtime(LocalDateTime.now());
        orderDTO.setOrderStatus(Constant.Status.ACTIVE);
        Order o = this.mapOrderDtoToOrder(orderDTO);
        o = this.orderRepository.saveAndFlush(o);
        Order finalOrder = o;
        List<OrderDetails> orderDetails = orderDTO.getOrderDetails().stream().map(oDetail -> {
            ProductDetail pro = ProductDetail
                    .builder()
                    .id(oDetail.getProductDetail().getId())
                    .build();
            return OrderDetails
                    .builder()
                    .productDetail(pro)
                    .id(oDetail.getId())
                    .price(oDetail.getPrice())
                    .quantity(oDetail.getQuantity())
                    .exchangeId(oDetail.getExchangeId())
                    .statusExchange(oDetail.getStatusExchange())
                    .statusOrderDetail(oDetail.getStatusOrderDetail())
                    .order(finalOrder)
                    .build();
        }).collect(Collectors.toList());
        this.orderDetailsRepository.saveAll(orderDetails);
        return o;
    }

    @Transactional
    public Order update(OrderDTO orderDTO) {
        return null;
    }

    @Transactional
    public void delete(Integer id) {

    }

    @Transactional
    public boolean restore(Integer modelsId) {
        return false;
    }

    private OrderDTO mapOrderToOrderDTO(Order order) {
        System.out.println(order.getOrderDetails().size());
        return this.modelMapper.map(order, OrderDTO.class);
    }

    private Order mapOrderDtoToOrder(OrderDTO dto) {
        return this.modelMapper.map(dto, Order.class);
    }
}
