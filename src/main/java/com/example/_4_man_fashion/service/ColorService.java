package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Color;

public interface ColorService {

    PageDTO<ColorDTO> getAll(int offset, int limit, Integer status,String search);
    Color create(ColorDTO colorDTO);

    Color update(ColorDTO colorDTO);

    void delete(Integer id);
}
