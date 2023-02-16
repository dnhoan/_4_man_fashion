package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.DTOs.ModelsDto;
import com.example._4_man_fashion.DTOs.SizeDto;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Size;

import java.util.List;

public interface SizeService {
    PageDTO<SizeDto> getAll(int offset, int limit, Integer status, String search);

    List<Size> getAllNoActive();
    Size create(SizeDto sizeDto);
    Size update(SizeDto sizeDto);
    List<Size> findByName(String name);
    List<Size> findByNameNoActive(String name);
    void delete(Integer sizeId);

    boolean restore(Integer sizeId);
}