package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.entities.*;
import com.example._4_man_fashion.models.UpdateOrderStatus;
import com.example._4_man_fashion.repositories.*;
import com.example._4_man_fashion.repositories.CartItemRepository;
import com.example._4_man_fashion.repositories.LogOrderStatusRepository;
import com.example._4_man_fashion.repositories.OrderDetailsRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import com.example._4_man_fashion.utils.Const;
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

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private CustomerService customerService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailService mailService;

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

    @Override
    public PageDTO<OrderDTO> getOrderByCustomerId(int customerId, int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Order> page = this.orderRepository.getOrderByCustomerId(pageable, status, StringCommon.getLikeCondition(search), customerId);
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

    @Override
    public OrderDTO getOrderByOrderId(String orderId) {
        Optional<Order> order = this.orderRepository.getOrderByOrderId(orderId);
        if(order.isPresent()) {
            return  this.mapOrderToOrderDTO(order.get());
        }
        else throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("OrderID"));
    }

    @Transactional
    public LogOrderStatusDTO updateOrderStatus(UpdateOrderStatus updateOrderStatus) {
        Optional<Order> order = this.orderRepository.findById(updateOrderStatus.getOrderId());
        if (order.isEmpty()) {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("order id"));
        }
        this.orderRepository.updateOrderStatus(updateOrderStatus);
        LogOrderStatus logOrderStatus = new LogOrderStatus();
        logOrderStatus.setOrder(order.get());
        logOrderStatus.setTimes(LocalDateTime.now());
        logOrderStatus.setUser_change("Admin");
        logOrderStatus.setNote(updateOrderStatus.getNote());
        logOrderStatus.setCurrentStatus(order.get().getOrderStatus());
        logOrderStatus.setNewStatus(updateOrderStatus.getNewStatus());
        logOrderStatus.setAccounts(null);
        logOrderStatus.setProductDetails(null);
        logOrderStatus = this.logOrderStatusRepository.save(logOrderStatus);




        return this.modelMapper.map(logOrderStatus, LogOrderStatusDTO.class);
    }

    @Transactional
    public void updateOrderShopStatus(UpdateOrderStatus updateOrderStatus) {
        Optional<Order> order = this.orderRepository.findById(updateOrderStatus.getOrderId());
        if (order.isEmpty()) {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("order id"));
        }
        Optional<Account> account = this.accountRepository.getAccountByCustomerId(updateOrderStatus.getUserId());
        if (account.isEmpty()) {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("account id"));
        }
        this.orderRepository.updateOrderStatus(updateOrderStatus);
        LogOrderStatus logOrderStatus = new LogOrderStatus();
        logOrderStatus.setOrder(order.get());
        logOrderStatus.setTimes(LocalDateTime.now());
        logOrderStatus.setUser_change(account.get().getEmail() + " - " + account.get().getPhoneNumber());
        logOrderStatus.setNote(updateOrderStatus.getNote());
        logOrderStatus.setCurrentStatus(order.get().getOrderStatus());
        logOrderStatus.setNewStatus(updateOrderStatus.getNewStatus());
        logOrderStatus.setAccounts(null);
        logOrderStatus.setProductDetails(null);


        Optional<SendEmailStatus> sendEmailStatus = this.orderRepository.getEmailCustomerByOrderId(updateOrderStatus.getOrderId());
        System.out.println( "hihi " + sendEmailStatus.get());
        SendEmailStatus sendEmailStatus1 = sendEmailStatus.get();
        System.out.println( "hihi " + sendEmailStatus.get());

        int status = updateOrderStatus.getNewStatus();
        String nameStatus = status == 0 ? "Chờ xác nhận" :
                status < 1 ? "Đã xác nhận" :
                        status < 2 ? "Đang đóng gói" :
                                status < 3 ? "Đang vận chuyển" :
                                        status < 4 ? "Đã hoàn thành" :
                                                status < -2 ? "Đổi trả" : "Hủy đơn";
        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(sendEmailStatus1.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_NOTIFY);

            Map<String, Object> props = new HashMap<>();
            props.put("name", sendEmailStatus1.getName());
            props.put("id", sendEmailStatus1.getId());
            props.put("status", nameStatus);
            dataMail.setProps(props);
            System.out.println("hihi" + nameStatus);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.ORDER_STATUS);

        } catch (MessagingException exp){
            exp.printStackTrace();
        }

        this.logOrderStatusRepository.save(logOrderStatus);
    }

    @Transactional
    public Order create(OrderDTO orderDTO) {
        orderDTO.setCtime(LocalDateTime.now());
        Order o = this.mapOrderDtoToOrder(orderDTO);
        int maxId = this.orderRepository.getMaxOrderId();
        String orderId = "DH0" + (maxId + 1);
        o.setCustomerId(orderDTO.getCustomerId());
        o.setOrderId(orderId);
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
                    .statusOrderDetail(Constant.Status.ACTIVE)
                    .price(oDetail.getPrice())
                    .quantity(oDetail.getQuantity())
                    .order(finalOrder)
                    .build();
        }).collect(Collectors.toList());
        Order finalO = o;
        List<LogOrderStatus> logOrderStatuses = orderDTO.getLogsOrderStatus().stream().map(log -> {
            LogOrderStatus logOrderStatus = this.modelMapper.map(log, LogOrderStatus.class);
            logOrderStatus.setOrder(finalO);
            return logOrderStatus;
        }).toList();
        this.logOrderStatusRepository.saveAll(logOrderStatuses);
        this.orderDetailsRepository.saveAll(orderDetails);
        return o;
    }


    @Override
    @Transactional
    public Order createOrderOnline(OrderDTO orderDTO) {
        orderDTO.setCtime(LocalDateTime.now());
        Order o = this.mapOrderDtoToOrder(orderDTO);
        int maxId = this.orderRepository.getMaxOrderId();
        String orderId = "DH0" + (maxId + 1);
        o.setOrderId(orderId);
        o = this.orderRepository.saveAndFlush(o);
        Order finalOrder = o;
        List<OrderDetails> orderDetails = orderDTO.getOrderDetails().stream().map(oDetail -> {
            ProductDetail pro = ProductDetail
                    .builder()
                    .id(oDetail.getProductDetail().getId())
                    .build();
            return OrderDetails
                    .builder()
                    .statusOrderDetail(Constant.Status.ACTIVE)
                    .productDetail(pro)
                    .id(oDetail.getId())
                    .price(oDetail.getPrice())
                    .quantity(oDetail.getQuantity())
                    .quantityOrigin(oDetail.getQuantityOrigin())
                    .order(finalOrder)
                    .build();
        }).collect(Collectors.toList());
        Order finalO = o;
        List<LogOrderStatus> logOrderStatuses = orderDTO.getLogsOrderStatus().stream().map(log -> {
            LogOrderStatus logOrderStatus = this.modelMapper.map(log, LogOrderStatus.class);
            logOrderStatus.setTimes(LocalDateTime.now());
            logOrderStatus.setOrder(finalO);
            return logOrderStatus;
        }).toList();
        this.logOrderStatusRepository.saveAll(logOrderStatuses);
        this.orderDetailsRepository.saveAll(orderDetails);
        this.cartItemRepository.deleteCartItemsByCustomerId(o.getCustomerId());
        return o;
    }

    @Transactional
    public OrderDTO update(OrderDTO orderDTO) {
        Order o = this.mapOrderDtoToOrder(orderDTO);
        o.setCustomerId(orderDTO.getCustomerId());
        o = this.orderRepository.saveAndFlush(o);
        Order finalOrder = o;
        List<OrderDetails> orderDetails = orderDTO.getOrderDetails().stream().map(oDetail -> {
            ProductDetail pro = ProductDetail
                    .builder()
                    .id(oDetail.getProductDetail().getId())
                    .build();
            OrderDetails orderDetail = this.modelMapper.map(oDetail, OrderDetails.class);
            orderDetail.setOrder(finalOrder);
            orderDetail.setProductDetail(pro);
            return orderDetail;
        }).collect(Collectors.toList());
        Order finalO = o;
        List<LogOrderStatus> logOrderStatuses = orderDTO.getLogsOrderStatus().stream().map(log -> {
            LogOrderStatus logOrderStatus = this.modelMapper.map(log, LogOrderStatus.class);
            logOrderStatus.setOrder(finalO);
            return logOrderStatus;
        }).toList();
        this.logOrderStatusRepository.saveAll(logOrderStatuses);
        this.orderDetailsRepository.saveAll(orderDetails);
        return this.mapOrderToOrderDTO(o);
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
        if (order.getCustomerId() != null) {
            CustomerDTO customer = this.customerService.getCustomerById(order.getCustomerId());
            orderDTO.setCustomerInfo(customer);
        }
        List<LogOrderStatusDTO> logOrderStatusDTOS = this.logOrderStatusRepository
                .getLogOrderStatusesByOrderIdOrderByTimesDesc(order.getId()).stream().map(log -> this.modelMapper.map(log, LogOrderStatusDTO.class)).toList();
        orderDTO.setLogsOrderStatus(logOrderStatusDTOS);
        return orderDTO;
    }

    private Order mapOrderDtoToOrder(OrderDTO dto) {
        return this.modelMapper.map(dto, Order.class);
    }
}
