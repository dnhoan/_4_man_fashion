package com.example._4_man_fashion.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example._4_man_fashion.dto.CartItemDTO;
import com.example._4_man_fashion.dto.ProductDetailDTO;
import com.example._4_man_fashion.entities.Cart;
import com.example._4_man_fashion.entities.CartItem;
import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.repositories.CartItemRepository;
import com.example._4_man_fashion.repositories.CartRepository;

public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    private CartItemDTO cartItemMapToCartItemDto(CartItem cartItem) {
        ProductDetailDTO productDetailCartDto = this.cartRepository.getProductDetailByCartItemId(cartItem.getId());
        return CartItemDTO
                .builder()
                .id(cartItem.getId())
                .amount(cartItem.getAmount())
                .productDetailDto(productDetailCartDto)
                .build();
    }

    private CartItem cartItemDtoMapToCartItem(CartItemDTO cartItemDTO) {
        return CartItem
                .builder()
                .id(cartItemDTO.getId())
                .amount(cartItemDTO.getAmount())
                .productDetailId(cartItemDTO.getProductDetailDto().getId())
                .build();
    }

    @Override
    public CartItemDTO addCartItem(CartItemDTO cartItemDTO, Integer customerId) {
        Cart cart = new Cart();
        Optional<Cart> cartOptional = this.cartRepository.getCartByCustomerId(customerId);
        CartItem cartItem = this.cartItemDtoMapToCartItem(cartItemDTO);
        if (cartOptional.isPresent()) {
            Optional<CartItem> cartItemOptional = this.cartItemRepository.getCartItemByProductDetailIdAndCartId(
                    cartItem.getProductDetailId(),
                    cartOptional.get().getId());
            if (cartItemOptional.isPresent()) {
                cartItem = cartItemOptional.get();
                int amount = cartItem.getAmount();
                amount++;
                cartItem.setAmount(amount);
            }
            cartItem.setCart(cartOptional.get());
        } else {
            cart = Cart.builder()
                    .customer(Customer.builder().id(customerId).build())
                    .itemsAmount(0)
                    .totalMoney(0.0)
                    .build();
            this.cartRepository.save(cart);
            cartItem.setCart(cart);
        }
        cartItem = this.cartItemRepository.saveAndFlush(cartItem);
        return this.cartItemMapToCartItemDto(cartItem);
    }

    @Override
    public CartItemDTO updateCartItem(CartItemDTO cartItemDTO, Integer cart_id) {
        CartItem cartItem = cartItemDtoMapToCartItem(cartItemDTO);
        Cart cart = new Cart();
        cart.setId(cart_id);
        cartItem.setCart(cart);
        cartItem = this.cartItemRepository.save(cartItem);
        return this.cartItemMapToCartItemDto(cartItem);
    }

    @Override
    public void deleteCartItem(Integer id) {
        this.cartItemRepository.deleteById(id);
    }

}
