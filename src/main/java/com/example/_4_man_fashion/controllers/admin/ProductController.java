package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.Service.ProductService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping("product/getAll")
    public ResponseEntity<ApiResponse<PageDTO<ProductDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "1") Integer status,
                                                                 @RequestParam(defaultValue = "") String search) {
        PageDTO<ProductDTO> result = productService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/product/create")
    public ResponseEntity<ApiResponse<Product>> create(@Valid @RequestBody ProductDTO dto) {
        Product product = productService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @PutMapping("/product/update")
    public ResponseEntity<ApiResponse<Product>> update(@Valid @RequestBody ProductDTO dto) {
        Product product = productService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
