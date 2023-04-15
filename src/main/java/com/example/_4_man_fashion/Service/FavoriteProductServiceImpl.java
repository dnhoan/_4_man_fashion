package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.FavoriteProductDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.FavoriteProduct;
import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.repositories.FavoriteProductRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteProductServiceImpl {

    @Autowired
    private FavoriteProductRepository favoriteProductRepository;
    @Autowired
    private ModelMapper modelMapper;


    public PageDTO<ProductDTO> getAll(int offset, int limit, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Product> page = this.favoriteProductRepository.getFavoriteProductByProductName(pageable, StringCommon.getLikeCondition(search));
        List<ProductDTO> productDTOList = page.stream().map(u -> this.modelMapper.map(u, ProductDTO.class)).collect(Collectors.toList());
        return new PageDTO<ProductDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                productDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }


    public PageDTO<FavoriteProductDTO> getAllByCustomerId(int offset, int limit, Integer customer_Id){
        Pageable pageable = PageRequest.of(offset, limit);
        Page<FavoriteProduct>  page = this.favoriteProductRepository.getFavoriteProductByCustomerId(pageable, customer_Id);
        List<FavoriteProductDTO> favoriteProductDTOS = page.stream().map(u -> this.modelMapper.map(u, FavoriteProductDTO.class)).collect(Collectors.toList());
        return new PageDTO<FavoriteProductDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                favoriteProductDTOS,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Transactional
    public FavoriteProduct create(FavoriteProductDTO favoriteProductDTO) {

        if(favoriteProductRepository.existsFavoriteProductByProductId(favoriteProductDTO.getProduct().getId())){
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Sản phẩm này"));
        }

        favoriteProductDTO.setCtime(LocalDateTime.now());

        return this.favoriteProductRepository.save(FavoriteProduct.fromDTO(favoriteProductDTO));

    }


    public void delete(Integer id){
        Optional<FavoriteProduct> optionalFavoriteProduct = this.favoriteProductRepository.findById(id);
        FavoriteProduct favoriteProduct = optionalFavoriteProduct.get();
        this.favoriteProductRepository.delete(favoriteProduct);
    }
}
