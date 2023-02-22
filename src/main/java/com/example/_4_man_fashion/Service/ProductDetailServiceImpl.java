package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ProductDetailDTO;
import com.example._4_man_fashion.entities.ProductDetail;
import com.example._4_man_fashion.repositories.ProductDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl implements ProductDetailService{

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<ProductDetailDTO> getProductDetailsByProductId(Integer product_id, Integer status) {
        List<ProductDetail> productDetails = this.productDetailRepository.getProductDetailsByProductId(product_id, status);
        return productDetails.stream().map(p -> this.modelMapper.map(p, ProductDetailDTO.class)).collect(Collectors.toList());
    }
}
