package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.MaterialDTO;
import com.example._4_man_fashion.dto.PageDTO;

public interface MaterialService {
    PageDTO<MaterialDTO> getAll(int offset, int limit, Integer status);

    void create(MaterialDTO materialDTO);

    void update(MaterialDTO materialDTO);

    void delete(Integer id);
}
