package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.DTOs.ModelsDto;
import com.example._4_man_fashion.entities.Models;
import com.example._4_man_fashion.repositories.ModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModelsServiceImp implements ModelsService {
    @Autowired
    private ModelsRepository modelsRepository;




    private ModelsDto modelsMapToModelsDto(Models models) {
        return ModelsDto.builder()
                .id(models.getId())
                .models_name(models.getModels_name())
                .status(models.getStatus())
                .build();
    }

    private Models modelsDtoMapToModels(ModelsDto modelsDto) {
        Models models = Models.builder()
                .id(modelsDto.getId())
                .models_name(modelsDto.getModels_name())
                .status(1)
                .build();

        return models;
    }

    private Models updateModels(ModelsDto modelsDto) {
        Models models = Models.builder()
                .models_name(modelsDto.getModels_name())
                .build();

        return models;
    }


    @Override
    public List<Models> getAll() {
        return this.modelsRepository.getAllActive();
    }

    @Override
    public List<Models> getAllNoActive() {
        return this.modelsRepository.getAllNoActive();
    }

    @Transactional
    public ModelsDto create(ModelsDto modelsDto) {
        Models models1 = this.modelsRepository.save(this.modelsDtoMapToModels(modelsDto));
        return modelsMapToModelsDto(models1);
    }

    @Transactional
    public ModelsDto update(ModelsDto modelsDto) {
        Models models1 = this.modelsRepository.saveAndFlush(this.updateModels(modelsDto));
        return modelsMapToModelsDto(models1);
    }

    @Override
    public List<Models> findByName(String name) {
        return this.modelsRepository.findByName(name);

    }

    @Override
    public List<Models> findByNameNoActive(String name) {
        return this.modelsRepository.findByNameNoActive(name);
    }

    @Override
    @Transactional
    public boolean delete(Integer modelsId) {
        try {
            this.modelsRepository.deleteModels(modelsId);
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean restore(Integer modelsId) {
        try {
            this.modelsRepository.restoreModels(modelsId);
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
