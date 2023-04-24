package com.example._4_man_fashion.controllers.user;

import com.example._4_man_fashion.Service.FavoriteProductServiceImpl;
import com.example._4_man_fashion.Service.StatisticServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.FavoriteProduct;
import com.example._4_man_fashion.utils.ApiResponse;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.USER)
public class FavoriteProductController {
    @Autowired
    public FavoriteProductServiceImpl favoriteProductService;

    @Autowired
    public StatisticServiceImpl statisticService;

    @GetMapping("favorite/getList/{customerId}")
    public ResponseEntity<ApiResponse<PageDTO<ProductDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @PathVariable() Integer customerId) {
        PageDTO<ProductDTO> result = favoriteProductService.getAllByCustomerId(offset, limit, customerId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("favorite/getFavoriteProductByCustomerId/{customerId}")
    public ResponseEntity<ApiResponse<List<FavoriteProductList>>> getFavoriteProductByCustomerId(@PathVariable() Integer customerId) {
        List<FavoriteProductList> result = favoriteProductService.getFavoriteProductByCustomer( customerId);
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

    @GetMapping("/favorite/statisticFavoriteProduct")
    public ResponseEntity<ApiResponse<List<StatisticFavorite>>>statisticFavoriteProducts() {

            List<StatisticFavorite> statisticFavorites = this.statisticService.statisticsByBestFavoriteProductsOnline();
            return ResponseEntity.ok(ApiResponse.success(statisticFavorites));


    }
}
