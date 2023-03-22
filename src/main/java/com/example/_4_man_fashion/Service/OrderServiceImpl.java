package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Order;
import com.example._4_man_fashion.entities.OrderDetails;
import com.example._4_man_fashion.entities.ProductDetail;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import com.example._4_man_fashion.repositories.OrderDetailsRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageDTO<OrderDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Order> page = this.orderRepository.getAllOrder(pageable, status, StringCommon.getLikeCondition(search));
        List<OrderDTO> orderDTOList = page.stream().map(u -> this.mapOrderToOrderDTO(u)).collect(Collectors.toList());
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
        this.orderRepository.updateOrderStatus(updateOrderStatus);
    }

    @Transactional
    public Order create(OrderDTO orderDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        orderDTO.setOrderId(timestamp.getTime() + "");
        orderDTO.setCtime(LocalDateTime.now());
        orderDTO.setOrderStatus(Constant.Status.ACTIVE);
        Order o = this.mapOrderDtoToOrder(orderDTO);
        final Order o2 = this.orderRepository.saveAndFlush(o);
        List<OrderDetails> orderDetails = orderDTO.getOrderDetails().stream().map(oDetail -> {
            ProductDetail pro = oDetail.getProductDetail();
            pro.setOrderDetails(oDetail);
            oDetail.setProductDetail(pro);
            oDetail.setOrder(o2);
            return oDetail;
        }).toList();
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
        OrderDTO orderDTO = this.modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }

    private Order mapOrderDtoToOrder(OrderDTO dto) {
        Order o = this.modelMapper.map(dto, Order.class);
        return o;
    }
}
