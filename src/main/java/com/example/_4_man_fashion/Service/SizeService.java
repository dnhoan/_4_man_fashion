package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.DTOs.SizeDto;
import com.example._4_man_fashion.entities.Size;

import java.util.List;

public interface SizeService {
    List<Size> getAll();

    List<Size> getAllNoActive();
    SizeDto create(SizeDto sizeDto);
    SizeDto update(SizeDto sizeDto);
    List<Size> findByName(String name);
    List<Size> findByNameNoActive(String name);
    boolean delete(Integer sizeId);

    boolean restore(Integer sizeId);
}