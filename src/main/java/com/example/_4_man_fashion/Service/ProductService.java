package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.repositories.ProductRepository;
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
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public PageDTO<ProductDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Product> page = this.productRepository.getProductByName(pageable, status, StringCommon.getLikeCondition(search));
        List<ProductDTO> productDTOList = page.stream().map(pro -> this.modelMapper.map(pro, ProductDTO.class)).collect(Collectors.toList());
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

    @Transactional
    public Product create(ProductDTO productDTO) {
        if(StringCommon.isNullOrBlank(productDTO.getProductName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistColorName = productRepository.existsByProductNameLike(productDTO.getProductName().trim());
        if(isExistColorName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên sản phẩm"));
        }

        productDTO.setCtime(LocalDateTime.now());
        productDTO.setStatus(Constant.Status.ACTIVE);

        return this.productRepository.save(Product.fromDTO(productDTO));

    }

    public Product update(ProductDTO productDTO) {
        Optional<Product> products = this.productRepository.findById(productDTO.getId());
        if(products.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Mã sản phẩm"));

        if(StringCommon.isNullOrBlank(productDTO.getProductName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Tên sản phẩm"));

        Product product = products.get();
        if(!product.getProductName().equals(productDTO.getProductName())) {
            boolean isExistProductName = productRepository.existsByProductNameLike(productDTO.getProductName().trim());
            if(isExistProductName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên sản phẩm"));
            }
        }

        product.setCategoryName(productDTO.getProductName());
        product.setProductId(productDTO.getProductId());
        product.setMtime(LocalDateTime.now());
        product.setStatus(productDTO.getStatus());
        return this.productRepository.save(product);
    }

    public void delete(Integer id) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if(optionalProduct.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Mã sản phẩm"));

        Product product = optionalProduct.get();
        product.setMtime(LocalDateTime.now());
        product.setStatus(Constant.Status.INACTIVE);

        this.productRepository.save(product);
    }
}
