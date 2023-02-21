package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ModelsDto;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Models;
import com.example._4_man_fashion.repositories.ModelsRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelsServiceImp implements ModelsService {
    @Autowired
    private ModelsRepository modelsRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PageDTO<ModelsDto> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Models> page = this.modelsRepository.getModelsByName(pageable, status, StringCommon.getLikeCondition(search));
        List<ModelsDto> modelsDTOList = page.stream().map(u -> this.modelMapper.map(u, ModelsDto.class)).collect(Collectors.toList());

        return new PageDTO<ModelsDto>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                modelsDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }


    @Transactional
    public Models create(ModelsDto modelsDto) {
        if(StringCommon.isNullOrBlank(modelsDto.getModelsName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistModelsName = modelsRepository.existsByModelsNameLike(modelsDto.getModelsName().trim());
        if(isExistModelsName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên kiểu dáng"));
        }

        modelsDto.setCtime(LocalDateTime.now());
        modelsDto.setStatus(Constant.Status.ACTIVE);

        return this.modelsRepository.save(Models.fromDTO(modelsDto));

    }

    public Models update(ModelsDto modelsDto) {
        Optional<Models> optionalModels = this.modelsRepository.findById(modelsDto.getId());
        if(optionalModels.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Models Id"));

        if(StringCommon.isNullOrBlank(modelsDto.getModelsName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Tên kiểu dáng"));

        Models models = optionalModels.get();
        if(!models.getModelsName().equals(modelsDto.getModelsName())) {
            boolean isExistModelsName = modelsRepository.existsByModelsNameLike(modelsDto.getModelsName().trim());
            if(isExistModelsName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên kiểu dáng"));
            }
        }

        models.setModelsName(modelsDto.getModelsName());
        models.setMtime(LocalDateTime.now());
        models.setStatus(modelsDto.getStatus());
        return this.modelsRepository.save(models);
    }

    public void delete(Integer id) {
        Optional<Models> optionalModels = this.modelsRepository.findById(id);
        if(optionalModels.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Models Id"));

        Models models = optionalModels.get();
        models.setMtime(LocalDateTime.now());
        models.setStatus(Constant.Status.INACTIVE);

        this.modelsRepository.save(models);
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


