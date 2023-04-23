package com.example._4_man_fashion.controllers.user;

import com.example._4_man_fashion.Service.ProductService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.models.SearchProduct;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(Constant.Api.Path.USER)
public class ProductOnlineController {
    @Autowired
    public ProductService productService;

    @PostMapping("product/searchProduct")
    public ResponseEntity<ApiResponse<PageDTO<ProductDTO>>> searchProduct(@RequestBody SearchProduct searchProduct) {
        PageDTO<ProductDTO> result = this.productService.searchProduct(searchProduct);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("getMinMaxPrice")
    public ResponseEntity<ApiResponse<List<Float>>> getPrice() {
        List<Float> result = this.productService.getMinMaxPrice();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("product/getAll")
    public ResponseEntity<ApiResponse<PageDTO<ProductDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                   @RequestParam(defaultValue = "10") int limit,
                                                                   @RequestParam(defaultValue = "1") Integer status,
                                                                   @RequestParam(defaultValue = "") String search) {
        PageDTO<ProductDTO> result = productService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
