package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.CategoryDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Category;

public interface CategoryService {
    PageDTO<CategoryDTO> getAll(int offset, int limit, Integer status, String search);

    Category create(CategoryDTO categoryDTO);

    Category update(CategoryDTO categoryDTO);

    void delete(Integer id);
}
