package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.MaterialDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Material;

public interface MaterialService {
    PageDTO<MaterialDTO> getAll(int offset, int limit, Integer status, String search);

    Material create(MaterialDTO materialDTO);

    Material update(MaterialDTO materialDTO);

    void delete(Integer id);
}
