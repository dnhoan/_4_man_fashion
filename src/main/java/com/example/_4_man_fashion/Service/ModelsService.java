package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.DTOs.ModelsDto;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Models;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ModelsService {

    PageDTO<ModelsDto> getAll(int offset, int limit, Integer status, String search);

    List<Models> getAllNoActive();
    Models create(ModelsDto modelsDto);
    Models update(ModelsDto modelsDto);
    List<Models> findByName(String name);
    List<Models> findByNameNoActive(String name);
    void delete(Integer modelsId);

    boolean restore(Integer modelsId);
}
