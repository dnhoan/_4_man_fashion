package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
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
import java.util.stream.Collectors;

@Service
public class ColorServiceImpl {
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<ColorDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Color> page = this.colorRepository.getAllById(pageable, status);
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
    public Color create(ColorDTO colorDTO) {
        Color old = colorRepository.findByCode(colorDTO.getColorCode()).orElse(null);
        Color color = this.colorRepository.findColorByColorName(colorDTO.getColorName());
        if (color != null && color.getStatus() == Constant.Status.ACTIVE) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Màu sắc"));
        }
        if (old != null && old.getStatus() == Constant.Status.ACTIVE) {
        }
        if (old != null && old.getStatus() == Constant.Status.INACTIVE) {
            colorRepository.delete(old);
        }
        colorDTO.setCtime(LocalDateTime.now());
        colorDTO.setStatus(Constant.Status.ACTIVE);

        return this.colorRepository.save(Color.fromDTO(colorDTO));

    }

    public Color update(ColorDTO colorDTO) {
        Color color = this.colorRepository.findColorById(colorDTO.getId());
        if (color != null) {
            color.setColorName(colorDTO.getColorName());
            color.setMtime(LocalDateTime.now());
            color.setStatus(Constant.Status.ACTIVE);
        }
        return this.colorRepository.saveAndFlush(Color.fromDTO(colorDTO));
    }

    public void delete(Integer id) {
        Color color = this.colorRepository.findColorById(id);
        if (color != null) {
            color.setMtime(LocalDateTime.now());
            color.setStatus(Constant.Status.INACTIVE);
            this.colorRepository.save(color);
        }
    }


}
