package com.example._4_man_fashion.controllers.user;

import com.example._4_man_fashion.Service.*;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.Api.Path.USER)
public class PropertiesController {

    @Autowired
    public CategoryServiceImpl cateService;
    @Autowired
    private ModelsService modelsService;
    @Autowired
    public ColorServiceImpl colorService;
    @Autowired
    private MaterialServiceImpl materialService;
    @Autowired
    private SizeService sizeService;

    @GetMapping("categories")
    public ResponseEntity<ApiResponse<PageDTO<CategoryDTO>>> getCategories(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "1") Integer status,
            @RequestParam(defaultValue = "") String search) {
        PageDTO<CategoryDTO> result = cateService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("colors")
    public ResponseEntity<ApiResponse<PageDTO<ColorDTO>>> getColors(@RequestParam(defaultValue = "0") int offset,
                                                                    @RequestParam(defaultValue = "10") int limit,
                                                                    @RequestParam(defaultValue = "1") Integer status,
                                                                    @RequestParam(defaultValue = "") String search) {
        PageDTO<ColorDTO> result = colorService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("materials")
    public ResponseEntity<ApiResponse<PageDTO<MaterialDTO>>> getMaterials(@RequestParam(defaultValue = "0") int offset,
                                                                          @RequestParam(defaultValue = "10") int limit,
                                                                          @RequestParam(defaultValue = "1") Integer status,
                                                                          @RequestParam(defaultValue = "") String search) {
        PageDTO<MaterialDTO> result = materialService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("models")
    public ResponseEntity<ApiResponse<PageDTO<ModelsDto>>> getModels(@RequestParam(defaultValue = "0") int offset,
                                                                     @RequestParam(defaultValue = "10") int limit,
                                                                     @RequestParam(defaultValue = "1") Integer status,
                                                                     @RequestParam(defaultValue = "") String search) {
        PageDTO<ModelsDto> result = modelsService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("sizes")
    public ResponseEntity<ApiResponse<PageDTO<SizeDto>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                @RequestParam(defaultValue = "10") int limit,
                                                                @RequestParam(defaultValue = "1") Integer status,
                                                                @RequestParam(defaultValue = "") String search) {
        PageDTO<SizeDto> result = sizeService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
