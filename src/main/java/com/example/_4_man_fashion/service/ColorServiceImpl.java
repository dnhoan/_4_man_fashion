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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example._4_man_fashion.utils.ErrorMessage.DUPLICATE_PARAMS;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageDTO<ColorDTO> getAll(int offset, int limit, Integer status) {
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

    @Override
    public void create(ColorDTO colorDTO) {
        Color old = colorRepository.findByCode(colorDTO.getColorCode()).orElse(null);
        Color color = this.colorRepository.findColorByColorName(colorDTO.getColorName());
        if (color != null && color.getStatus() == Constant.Status.ACTIVE) {
            throw new DATNException(ErrorMessage.OBJECT_ALREADY_EXIST.format("Màu sắc"));
        }
        if (old != null && old.getStatus() == Constant.Status.ACTIVE) {
            throw new DATNException(ErrorMessage.OBJECT_ALREADY_EXIST.format("Mã màu sắc"));
        }
        if (old != null && old.getStatus() == Constant.Status.INACTIVE) {
            colorRepository.delete(old);
        }
        colorDTO.setCtime(LocalDateTime.now());
        colorDTO.setStatus(Constant.Status.ACTIVE);

        this.colorRepository.save(Color.fromDTO(colorDTO));
    }

    @Override
    public void update(ColorDTO colorDTO) {
        Color color = this.colorRepository.findColorById(colorDTO.getId());
        if (color != null) {
            color.setColorName(colorDTO.getColorName());
            color.setMtime(LocalDateTime.now());
            color.setStatus(Constant.Status.ACTIVE);
        }
        this.colorRepository.save(Color.fromDTO(colorDTO));
    }

    @Override
    public void delete(Integer id) {
        Color color = this.colorRepository.findColorById(id);
        if (color != null) {
            color.setMtime(LocalDateTime.now());
            color.setStatus(Constant.Status.INACTIVE);
            this.colorRepository.save(color);
        }
    }


}
