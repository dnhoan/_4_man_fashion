package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.LogOrderStatusService;
import com.example._4_man_fashion.Service.SizeService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.LogOrderStatusDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.SizeDto;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class LogOrderStatusController {

    @Autowired
    private LogOrderStatusService logOrderStatusService;

    @GetMapping("logOrderStatus/getAll")
    public ResponseEntity<ApiResponse<PageDTO<LogOrderStatusDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                          @RequestParam(defaultValue = "10") int limit,
                                                                          @RequestParam(defaultValue = "") Integer id) {
        PageDTO<LogOrderStatusDTO> result = logOrderStatusService.getAll(offset, limit, id);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
