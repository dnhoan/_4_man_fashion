package com.example._4_man_fashion.service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.MaterialDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Material;
import com.example._4_man_fashion.repositories.MaterialRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageDTO<MaterialDTO> getAll(int offset, int limit, Integer status) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Material> page = this.materialRepository.getAllById(pageable);
        List<MaterialDTO> materialDTOList = page.stream().map(u -> this.modelMapper.map(u, MaterialDTO.class)).collect(Collectors.toList());
        return new PageDTO<MaterialDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                materialDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Override
    public void create(MaterialDTO materialDTO) {
        Material material = this.materialRepository.findMaterialByMaterialName(materialDTO.getMaterialName());
        if (material != null && material.getStatus() == Constant.Status.ACTIVE) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Chất liệu"));
        }
        if (material != null && material.getStatus() == Constant.Status.INACTIVE) {
            this.materialRepository.delete(material);
        }
        materialDTO.setCtime(LocalDateTime.now());
        materialDTO.setStatus(Constant.Status.ACTIVE);
        this.materialRepository.save(Material.fromDTO(materialDTO));
    }

    @Override
    public void update(MaterialDTO materialDTO) {
        Material material = this.materialRepository.findMaterialById(materialDTO.getId());
        if (material != null) {
            material.setMaterialName(materialDTO.getMaterialName());
            material.setMtime(LocalDateTime.now());
            material.setStatus(Constant.Status.ACTIVE);
        }

        this.materialRepository.save(Material.fromDTO(materialDTO));
    }

    @Override
    public void delete(Integer id) {
        Material material = this.materialRepository.findMaterialById(id);
        if (material != null) {
            material.setMtime(LocalDateTime.now());
            material.setStatus(Constant.Status.INACTIVE);
            this.materialRepository.save(material);
        }
    }
}
