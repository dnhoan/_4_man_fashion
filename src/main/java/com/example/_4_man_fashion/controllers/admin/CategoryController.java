package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.CategoryServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CategoryDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Category;
import com.example._4_man_fashion.utils.ApiResponse;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class CategoryController {
    @Autowired
    public CategoryServiceImpl cateService;

    @GetMapping("category/getAll")
    public ResponseEntity<ApiResponse<PageDTO<CategoryDTO>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "1") Integer status,
            @RequestParam(defaultValue = "") String search) {
        PageDTO<CategoryDTO> result = cateService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("category/getList")
    public ResponseEntity<ApiResponse<List<Category>>> getList() {
        List<Category> lstCate = this.cateService.getListCate();
        return ResponseEntity.ok(ApiResponse.success(lstCate));
    }

    @PostMapping("/category/create")
    public ResponseEntity<ApiResponse<Category>> create(@Valid @RequestBody CategoryDTO dto) {
        Category category = cateService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(category));
    }

    @PutMapping("/category/update")
    public ResponseEntity<ApiResponse<Category>> update(@Valid @RequestBody CategoryDTO dto) {
        Category category = cateService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(category));
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        cateService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
