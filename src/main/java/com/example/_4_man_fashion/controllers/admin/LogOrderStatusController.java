package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.LogOrderStatusService;
import com.example._4_man_fashion.Service.SizeService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.LogOrderStatusDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.SizeDto;
import com.example._4_man_fashion.entities.LogOrderStatus;
import com.example._4_man_fashion.entities.Material;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class LogOrderStatusController {

    @Autowired
    private LogOrderStatusService logOrderStatusService;

    @GetMapping("logOrderStatus/getList")
    public ResponseEntity<ApiResponse<List<LogOrderStatus>>> getList(@RequestParam(defaultValue = "") Integer orderId) {
        List<LogOrderStatus> logOrderStatuses = this.logOrderStatusService.getListLog(orderId);
        return ResponseEntity.ok(ApiResponse.success(logOrderStatuses));
    }
}
