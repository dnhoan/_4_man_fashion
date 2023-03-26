package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.entities.LogOrderStatus;
import com.example._4_man_fashion.repositories.LogOrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogOrderStatusServiceImpl implements LogOrderStatusService{

    @Autowired
    private LogOrderStatusRepository logOrderStatusRepository;

    @Override
    @Transactional
    public List<LogOrderStatus> getListLog(Integer orderId) {
        List<LogOrderStatus> logOrderStatuses = this.logOrderStatusRepository.getAllById(orderId);
        return logOrderStatuses;
    }
}
