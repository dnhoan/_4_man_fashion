package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.ProductDetailServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ProductDetailDTO;
import com.example._4_man_fashion.entities.ProductDetail;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class ProductDetailController {
    @Autowired
    private ProductDetailServiceImpl productDetailService;

    @GetMapping("productDetail/getByProductId")
    public ResponseEntity<ApiResponse<List<ProductDetailDTO>>> getByProductId(@RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "status",defaultValue = "1") Integer status) {
        List<ProductDetailDTO> lsProductDetails = this.productDetailService.getProductDetailsByProductId(product_id, status);
        return ResponseEntity.ok(ApiResponse.success(lsProductDetails));
    }

//    @PostMapping("productDetail/create")
//    public ResponseEntity<ApiResponse<ProductDetail>> create(@RequestBody ProductDetailDTO productDetailDTO) {
//        ProductDetail productDetail = productDetailService.create(productDetailDTO);
//        return ResponseEntity.ok(ApiResponse.success(productDetail));
//    }
}
