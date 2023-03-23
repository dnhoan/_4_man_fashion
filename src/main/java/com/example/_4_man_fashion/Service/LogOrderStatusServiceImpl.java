package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.LogOrderStatusDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.LogOrderStatus;
import com.example._4_man_fashion.repositories.LogOrderStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogOrderStatusServiceImpl implements LogOrderStatusService{

    @Autowired
    private LogOrderStatusRepository logOrderStatusRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PageDTO<LogOrderStatusDTO> getAll(int offset, int limit, Integer id) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<LogOrderStatus> page = this.logOrderStatusRepository.getAllById(pageable, id);
        List<LogOrderStatusDTO> logOrderStatusDTOList = page.stream().map(u -> this.modelMapper.map(u, LogOrderStatusDTO.class)).collect(Collectors.toList());
        return new PageDTO<LogOrderStatusDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                logOrderStatusDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
