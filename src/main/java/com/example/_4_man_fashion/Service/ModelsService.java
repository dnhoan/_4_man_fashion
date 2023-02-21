package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ModelsDto;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Models;

public interface ModelsService {

    PageDTO<ModelsDto> getAll(int offset, int limit, Integer status, String search);

    Models create(ModelsDto modelsDto);
    Models update(ModelsDto modelsDto);

    void delete(Integer modelsId);

    boolean restore(Integer modelsId);
}
