package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.FavoriteProduct;
import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.entities.Size;
import com.example._4_man_fashion.repositories.ColorRepository;
import com.example._4_man_fashion.repositories.FavoriteProductRepository;
import com.example._4_man_fashion.repositories.SizeRepository;
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
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SizeRepository sizeRepository;


    public PageDTO<ProductDTO> getAllByCustomerId(int offset, int limit, Integer customer_Id){
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Product>  page = this.favoriteProductRepository.getFavoriteProductByCustomerId(pageable, customer_Id);
        List<ProductDTO> productDTOList = page.stream().map(product -> {
            ProductDTO productDTO =  this.modelMapper.map(product, ProductDTO.class);
            List<Color> colors = this.colorRepository.getColorsByProductId(product.getId());
            List<Size> sizes = this.sizeRepository.getSizesByProductId(product.getId());
            productDTO.setColors(colors);
            productDTO.setSizes(sizes);
            return productDTO;
        }).collect(Collectors.toList());
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

    public List<FavoriteProductList> getFavoriteProductByCustomer(Integer customer_Id){
        return this.favoriteProductRepository.getListFavoriveProductByCustomerId(customer_Id);
    }

    @Transactional
    public FavoriteProduct create(FavoriteProductDTO favoriteProductDTO) {

        if(favoriteProductRepository.existsFavoriteProductByProductIdAndCustomerId(favoriteProductDTO.getProduct().getId(), favoriteProductDTO.getCustomer().getId())){
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
