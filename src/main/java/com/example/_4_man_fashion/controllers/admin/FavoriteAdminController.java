package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.FavoriteProductServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class FavoriteAdminController {

    @Autowired
    public FavoriteProductServiceImpl favoriteProductService;

    @GetMapping("favorite/getAll")
    public ResponseEntity<ApiResponse<PageDTO<ProductDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                @RequestParam(defaultValue = "10") int limit,
                                                                @RequestParam(defaultValue = "") String search) {
//        PageDTO<ProductDTO> result = favoriteProductService.getAll(offset, limit, search);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
