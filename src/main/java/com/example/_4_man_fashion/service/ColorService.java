package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.PageDTO;


public interface ColorService {

    PageDTO<ColorDTO> getAll(int offset, int limit, Integer status);
    void create(ColorDTO colorDTO);

    void update(ColorDTO colorDTO);

    void delete(Integer id);
}
