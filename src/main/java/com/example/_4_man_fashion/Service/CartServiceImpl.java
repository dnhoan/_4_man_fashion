package com.example._4_man_fashion.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example._4_man_fashion.dto.ProductDetailDTO;
import com.example._4_man_fashion.entities.ProductDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example._4_man_fashion.dto.CartDTO;
import com.example._4_man_fashion.dto.CartItemDTO;
import com.example._4_man_fashion.dto.ProductDetailCartDTO;
import com.example._4_man_fashion.entities.Cart;
import com.example._4_man_fashion.entities.CartItem;
import com.example._4_man_fashion.repositories.CartRepository;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CartDTO> getAll() {
        return this.cartRepository.findAll()
                .stream()
                .map(this::cartMapToCartDto)
                .collect(Collectors.toList());
    }

    private CartDTO cartMapToCartDto(Cart cart) {
        List<CartItemDTO> cartItemDTOs = cart.getCartItems()
                .stream()
                .map(this::cartItemMapToCartItemDto)
                .collect(Collectors.toList());
        return CartDTO
                .builder()
                .id(cart.getId())
                .totalMoney(cart.getTotalMoney())
                .itemsAmount(cart.getItemsAmount())
                .cartItemDtos(cartItemDTOs)
                .build();
    }

    private Cart CartDTOMapToCart(CartDTO cartDTO) {
        List<CartItem> cartItems = cartDTO.getCartItemDtos()
                .stream().map(this::cartItemDtoMapToCartItem)
                .collect(Collectors.toList());

        Cart cart = Cart.builder()
                .totalMoney(cartDTO.getTotalMoney())
                .itemsAmount(cartDTO.getItemsAmount())
                .cartItems(cartItems)
                .build();
        // cart.setCartItems(cartItems);
        return cart;
    }

    private CartItem cartItemDtoMapToCartItem(CartItemDTO cartItemDTO) {
        return CartItem
                .builder()
                .id(cartItemDTO.getId())
                .amount(cartItemDTO.getAmount())
                .productDetailId(cartItemDTO.getProductDetailDTO().getId())
                // .productDetail(this.cartItemRepository.getProductDetailById(
                // cartItemDTO.getProductDetailCartDto().getId()
                // ))
                .build();
    }

    private CartItemDTO cartItemMapToCartItemDto(CartItem cartItem) {
        // get product detail by cart item id
        ProductDetail productDetail = this.cartRepository.getProductDetailByCartItemId(cartItem.getId());
        return CartItemDTO
                .builder()
                .id(cartItem.getId())
                .amount(cartItem.getAmount())
                .productDetailDTO(this.modelMapper.map(productDetail, ProductDetailDTO.class))
                .build();
    }

    @Override
    public CartDTO getCartByCustomerId(Integer cid) {
        Optional<Cart> cartOptional = this.cartRepository.getCartByCustomerId(cid);
        if (cartOptional.isPresent())
            return this.cartMapToCartDto(cartOptional.get());
        return new CartDTO();
    }

    @Override
    public CartDTO create(CartDTO cartDTO) {
        Cart cart = this.cartRepository.save(this.CartDTOMapToCart(cartDTO));
        return this.cartMapToCartDto(cart);
    }

    @Override
    public Cart update(Cart cart) {
        return this.cartRepository.save(cart);
    }

    @Override
    public void delete(Integer id) {
        this.cartRepository.deleteById(id);
    }

}
