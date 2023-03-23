package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.LogOrderStatusDTO;
import com.example._4_man_fashion.dto.PageDTO;

public interface LogOrderStatusService {

    PageDTO<LogOrderStatusDTO> getAll(int offset, int limit, Integer id);
}
