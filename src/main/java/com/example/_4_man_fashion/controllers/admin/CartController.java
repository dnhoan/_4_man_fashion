package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.CartDTO;
import com.example._4_man_fashion.dto.CartItemDTO;
import com.example._4_man_fashion.dto.ResponseDTO;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

import com.example._4_man_fashion.Service.CartItemService;
import com.example._4_man_fashion.Service.CartService;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("carts")
    public ResponseEntity<ResponseDTO> getCarts() {
        CartDTO cartDto = this.cartService.getCartByCustomerId(4);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartDto", cartDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("cart/{cus_id}")
    public ResponseEntity<ResponseDTO> getCartByCustomer(@PathVariable("cus_id") Integer cus_id) {
        CartDTO cartDto = this.cartService.getCartByCustomerId(cus_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartDto", cartDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build());
    }

    @PostMapping("cartItem/{customer_id}")
    public ResponseEntity<ResponseDTO> addCartItem(
            @PathVariable("customer_id") Integer customer_id,
            @RequestBody CartItemDTO cartItemDTO) {
        cartItemDTO = this.cartItemService.addCartItem(cartItemDTO, customer_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartItemDto", cartItemDTO))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build());
    }

    @PutMapping("cartItem/{cart_id}")
    public ResponseEntity<ResponseDTO> saveCartItem(@PathVariable("cart_id") Integer cart_id,
            @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO cartItemInsertDto = this.cartItemService.updateCartItem(cartItemDTO, cart_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartItemInsertDto", cartItemInsertDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping("cartItem/{citem_id}")
    public ResponseEntity<ResponseDTO> removeCartItem(@PathVariable("citem_id") Integer citem_id) {
        this.cartItemService.deleteCartItem(citem_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build());
    }
}
