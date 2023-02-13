package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.DTOs.ModelsDto;
import com.example._4_man_fashion.entities.Models;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ModelsService {

    List<Models> getAll();

    List<Models> getAllNoActive();
    ModelsDto create(ModelsDto modelsDto);
    ModelsDto update(ModelsDto modelsDto);
    List<Models> findByName(String name);
    List<Models> findByNameNoActive(String name);
    boolean delete(Integer modelsId);

    boolean restore(Integer modelsId);
}
