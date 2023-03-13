package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CartDTO;
import com.example._4_man_fashion.dto.CartItemDTO;
import com.example._4_man_fashion.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example._4_man_fashion.Service.CartItemService;
import com.example._4_man_fashion.Service.CartService;

@RestController
@RequestMapping(Constant.Api.Path.USER)
public class CartController {
        @Autowired
        private CartService cartService;

        @Autowired
        private CartItemService cartItemService;

        @GetMapping("carts")
        public ResponseEntity<ApiResponse<CartDTO>> getCarts() {
                CartDTO cartDto = this.cartService.getCartByCustomerId(1);
                return ResponseEntity.ok(ApiResponse.success(cartDto));
        }

        @GetMapping("cart/{cus_id}")
        public ResponseEntity<ApiResponse<CartDTO>> getCartByCustomer(@PathVariable("cus_id") Integer cus_id) {
                CartDTO cartDto = this.cartService.getCartByCustomerId(cus_id);
                return ResponseEntity.ok(ApiResponse.success(cartDto));
        }

        @PostMapping("cartItem/{customer_id}")
        public ResponseEntity<ApiResponse<CartItemDTO>> addCartItem(
                        @PathVariable("customer_id") Integer customer_id,
                        @RequestBody CartItemDTO cartItemDTO) {
                cartItemDTO = this.cartItemService.addCartItem(cartItemDTO, customer_id);
                return ResponseEntity.ok(ApiResponse.success(cartItemDTO));
        }

        @PutMapping("cartItem/{cart_id}")
        public ResponseEntity<ApiResponse<CartItemDTO>> saveCartItem(@PathVariable("cart_id") Integer cart_id,
                        @RequestBody CartItemDTO cartItemDTO) {
                CartItemDTO cartItemInsertDto = this.cartItemService.updateCartItem(cartItemDTO, cart_id);
                return ResponseEntity.ok(ApiResponse.success(cartItemInsertDto));
        }

        @DeleteMapping("cartItem/{citem_id}")
        public ResponseEntity<ApiResponse<CartItemDTO>> removeCartItem(@PathVariable("citem_id") Integer citem_id) {
                this.cartItemService.deleteCartItem(citem_id);
                return ResponseEntity.ok(ApiResponse.success());
        }
}
