package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.entities.LogOrderStatus;

import java.util.List;

public interface LogOrderStatusService {

    List<LogOrderStatus> getListLog(Integer orderId);
}
