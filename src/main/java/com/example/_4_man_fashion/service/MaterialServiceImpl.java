package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.MaterialDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Category;
import com.example._4_man_fashion.entities.Material;
import com.example._4_man_fashion.repositories.MaterialRepository;
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
public class MaterialServiceImpl {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<MaterialDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Material> page = this.materialRepository.getMaterialByName(pageable, status, StringCommon.getLikeCondition(search));
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


    @Transactional
    public List<Material> getListMate() {
        List<Material> lstMate = this.materialRepository.findAll();
        return lstMate;
    }
    @Transactional
    public Material create(MaterialDTO materialDTO) {
        if (StringCommon.isNullOrBlank(materialDTO.getMaterialName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistMaterialName = materialRepository.existsByMaterialNameLike(materialDTO.getMaterialName().trim());
        if (isExistMaterialName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Chất liệu"));
        }

        materialDTO.setCtime(LocalDateTime.now());
        materialDTO.setStatus(Constant.Status.ACTIVE);

        return this.materialRepository.save(Material.fromDTO(materialDTO));

    }

    public Material update(MaterialDTO materialDTO) {
        Optional<Material> optionalMaterial = this.materialRepository.findById(materialDTO.getId());
        if (optionalMaterial.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Chất liệu"));

        if (StringCommon.isNullOrBlank(materialDTO.getMaterialName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Chất liệu"));

        Material material = optionalMaterial.get();
        if (!material.getMaterialName().equals(materialDTO.getMaterialName())) {
            boolean isExistMaterialName = materialRepository.existsByMaterialNameLike(materialDTO.getMaterialName().trim());
            if (isExistMaterialName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Chất liệu"));
            }
        }

        material.setMaterialName(materialDTO.getMaterialName());
        material.setMtime(LocalDateTime.now());
        material.setStatus(materialDTO.getStatus());
        return this.materialRepository.save(material);
    }

    public void delete(Integer id) {
        Optional<Material> optionalMaterial = this.materialRepository.findById(id);
        if (optionalMaterial.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Chất liệu"));

        Material material = optionalMaterial.get();
        material.setMtime(LocalDateTime.now());
        material.setStatus(Constant.Status.INACTIVE);

        this.materialRepository.save(material);
    }
}
