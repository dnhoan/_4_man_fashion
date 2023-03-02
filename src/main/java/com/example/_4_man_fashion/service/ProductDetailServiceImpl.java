package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.MaterialDTO;
import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.dto.ProductDetailDTO;
import com.example._4_man_fashion.entities.Material;
import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.entities.ProductDetail;
import com.example._4_man_fashion.repositories.ProductDetailRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public List<ProductDetailDTO> getProductDetailsByProductId(Integer product_id, Integer status) {
        List<ProductDetail> productDetails = this.productDetailRepository.getProductDetailsByProductId(product_id, status);
        return productDetails.stream().map(p -> this.modelMapper.map(p, ProductDetailDTO.class)).collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public List<ProductDetailDTO> createProductDetailsByProductId(Integer product_id, List<ProductDetailDTO> productDetailDTOS) {
//        List<ProductDetail> productDetails = this.productDetailRepository.saveAll(productDetailDTOS);
//        return null;
//    }

//    @Transactional
//    public ProductDetail create(ProductDetailDTO productDetailDTO) {
//        List<Product> product = productDetailRepository.findByProductId(productDetailDTO.getProductId());
//        if (product.isEmpty()){
//            List<ProductDetail> lstProductDetail = productDetailRepository
//                    .getProductDetailsByProductId(productDetailDTO.getProductId(), productDetailDTO.getStatus());
//            System.out.println(lstProductDetail);
//
//        }

//        if (product.getId() == lstProductDetail && productDetailRepository.existsByColorAndSizeAndIdIsNot(
//                productDetailDTO.getColorName(),
//                productDetailDTO.getSizeName())
//        ) {
//
//        }

//        productDetailDTO.setStatus(Constant.Status.ACTIVE);
//
//        return this.productDetailRepository.save(ProductDetail.fromDTO(productDetailDTO));
//
//    }
}
