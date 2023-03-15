package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.FavoriteProductServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CartItemDTO;
import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.FavoriteProductDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.FavoriteProduct;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.USER)
public class FavoriteProductController {
    @Autowired
    public FavoriteProductServiceImpl favoriteProductService;

    @GetMapping("favorite/getList")
    public ResponseEntity<ApiResponse<PageDTO<FavoriteProductDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "1") Integer customerId) {
        PageDTO<FavoriteProductDTO> result = favoriteProductService.getAll(offset, limit, customerId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/favorite/create")
    public ResponseEntity<ApiResponse<FavoriteProduct>> create(@Valid @RequestBody FavoriteProductDTO dto) {
        FavoriteProduct favoriteProduct = favoriteProductService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(favoriteProduct));
    }

    @DeleteMapping("/favorite/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        favoriteProductService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
