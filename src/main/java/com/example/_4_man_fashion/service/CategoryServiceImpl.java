package com.example._4_man_fashion.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.entities.Color;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CategoryDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Category;
import com.example._4_man_fashion.repositories.CategoryRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageDTO<CategoryDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Category> page = this.categoryRepository.getCategoryByName(pageable, status,
                StringCommon.getLikeCondition(search));
        List<CategoryDTO> colorDTOList = page.stream().map(u -> this.modelMapper.map(u, CategoryDTO.class))
                .collect(Collectors.toList());
        return new PageDTO<CategoryDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                colorDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Transactional
    public List<Category> getListCate() {
        List<Category> lstCate = this.categoryRepository.findAll();
        return lstCate;
    }

    @Transactional
    public Category create(CategoryDTO categoryDTO) {
        if (StringCommon.isNullOrBlank(categoryDTO.getCategoryName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistCategoryName = categoryRepository.existsByCategoryName(categoryDTO.getCategoryName().trim());
        if (isExistCategoryName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Loại sản phẩm"));
        }

        categoryDTO.setCtime(LocalDateTime.now());
        categoryDTO.setStatus(Constant.Status.ACTIVE);

        return this.categoryRepository.save(Category.fromDTO(categoryDTO));

    }

    public Category update(CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryDTO.getId());
        if (optionalCategory.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Mã loại sản phẩm"));

        if (StringCommon.isNullOrBlank(categoryDTO.getCategoryName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Loại sản phẩm"));

        Category category = optionalCategory.get();
        if (!category.getCategoryName().equals(categoryDTO.getCategoryName())) {
            boolean isExistCategoryName = categoryRepository.existsByCategoryName(categoryDTO.getCategoryName().trim());
            if (isExistCategoryName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Loại sản phẩm"));
            }
        }

        category.setCategoryName(categoryDTO.getCategoryName());
        category.setMtime(LocalDateTime.now());
        category.setStatus(categoryDTO.getStatus());
        return this.categoryRepository.save(category);
    }

    public void delete(Integer id) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Category Id"));

        Category category = optionalCategory.get();
        category.setMtime(LocalDateTime.now());
        category.setStatus(Constant.Status.INACTIVE);

        this.categoryRepository.save(category);
    }

}
