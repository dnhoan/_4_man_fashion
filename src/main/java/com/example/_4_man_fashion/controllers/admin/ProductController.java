package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.ProductService;
import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.Service.ProductServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

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
    public ResponseEntity<ApiResponse<ProductDTO>> create(@Valid @RequestBody ProductDTO dto) {
        ProductDTO productDTO = this.productService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(productDTO));
    }

    @PutMapping("/product/update")
    public ResponseEntity<ApiResponse<ProductDTO>> update(@Valid @RequestBody ProductDTO dto) {
        ProductDTO product = productService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @PutMapping("/product/updateStatus/{id}")
    public ResponseEntity<ApiResponse<String>> updateStatus(@PathVariable Integer id, @RequestParam(value = "status", defaultValue = "0") Integer status) {
        productService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(Objects.equals(status, Constant.Status.ACTIVE) ? "Xóa sản phẩm thành công" : "Khôi phục sản phẩm thành công"));
    }
}
