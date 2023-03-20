package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.OrderDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Order;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageDTO<OrderDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Order> page = this.orderRepository.getAllOrder(pageable, status, StringCommon.getLikeCondition(search));
        List<OrderDTO> orderDTOList = page.stream().map(u -> this.modelMapper.map(u, OrderDTO.class)).collect(Collectors.toList());
        return new PageDTO<OrderDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                orderDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Transactional
    public List<Order> getListOrder() {
        return null;
    }

    @Transactional
    public Order create(OrderDTO orderDTO) {
        if(StringCommon.isNullOrBlank(orderDTO.getOrderId())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
        boolean isExistOrderId = orderRepository.existsByOrderId(orderDTO.getOrderId().trim());
        if(isExistOrderId) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("OrderId"));
        }
        orderDTO.setCtime(LocalDateTime.now());
        orderDTO.setOrderStatus(Constant.Status.ACTIVE);

        return this.orderRepository.save(Order.fromDTO(orderDTO));
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
}
