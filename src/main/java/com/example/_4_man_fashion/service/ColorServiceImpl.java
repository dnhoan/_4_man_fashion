package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Category;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.repositories.ColorRepository;
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
public class ColorServiceImpl {
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<ColorDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Color> page = this.colorRepository.getColorByName(pageable, status, StringCommon.getLikeCondition(search));
        List<ColorDTO> colorDTOList = page.stream().map(u -> this.modelMapper.map(u, ColorDTO.class)).collect(Collectors.toList());
        return new PageDTO<ColorDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                colorDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Transactional
    public List<Color> getListColor() {
        List<Color> lstColor = this.colorRepository.findAll();
        return lstColor;
    }

    @Transactional
    public Color create(ColorDTO colorDTO) {
        if (StringCommon.isNullOrBlank(colorDTO.getColorName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistColorName = colorRepository.existsByColorNameLike(colorDTO.getColorName().trim());
        if (isExistColorName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên màu sắc"));
        }

        colorDTO.setCtime(LocalDateTime.now());
        colorDTO.setStatus(Constant.Status.ACTIVE);

        return this.colorRepository.save(Color.fromDTO(colorDTO));

    }

    public Color update(ColorDTO colorDTO) {
        Optional<Color> optionalColor = this.colorRepository.findById(colorDTO.getId());
        if (optionalColor.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Color Id"));

        if (StringCommon.isNullOrBlank(colorDTO.getColorName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Tên màu sắc"));

        Color color = optionalColor.get();
        if (!color.getColorName().equals(colorDTO.getColorName())) {
            boolean isExistColorName = colorRepository.existsByColorNameLike(colorDTO.getColorName().trim());
            if (isExistColorName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên màu sắc"));
            }
        }

        color.setColorName(colorDTO.getColorName());
        color.setColorCode(colorDTO.getColorCode());
        color.setMtime(LocalDateTime.now());
        color.setStatus(colorDTO.getStatus());
        return this.colorRepository.save(color);
    }

    public void delete(Integer id) {
        Optional<Color> optionalColor = this.colorRepository.findById(id);
        if (optionalColor.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Color Id"));

        Color color = optionalColor.get();
        color.setMtime(LocalDateTime.now());
        color.setStatus(Constant.Status.INACTIVE);

        this.colorRepository.save(color);
    }


}
