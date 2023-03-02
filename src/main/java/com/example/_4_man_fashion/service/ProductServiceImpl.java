package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Category;
import com.example._4_man_fashion.entities.Product;
import com.example._4_man_fashion.entities.ProductDetail;
import com.example._4_man_fashion.repositories.ProductDetailRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Transactional
    public PageDTO<ProductDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Product> page = this.productRepository.getProductByName(pageable, status, StringCommon.getLikeCondition(search));
        List<ProductDTO> productDTOList = page.stream().map(product -> this.modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
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
    public List<Product> getListProduct() {
        List<Product> lstProduct = this.productRepository.findAll();
        return lstProduct;
    }

    @Override
    public ProductDTO getById(Integer id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return this.modelMapper.map(product.get(), ProductDTO.class);
        }
        return null;
    }


    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        if (StringCommon.isNullOrBlank(productDTO.getProductName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistColorName = productRepository.existsByProductNameLike(productDTO.getProductName().trim());
        if (isExistColorName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên sản phẩm"));
        }

        Product product = this.modelMapper.map(productDTO, Product.class);
        Product finalProduct = product;

        product.setProductImages(product.getProductImages().stream().peek(productImage -> productImage.setProduct(finalProduct)).collect(Collectors.toList()));

        List<ProductDetail> productDetails = productDTO.getProductDetails().stream().map(productDetailDTO -> {
            ProductDetail productDetail = this.modelMapper.map(productDetailDTO, ProductDetail.class);
            productDetail.setProduct(finalProduct);
            return productDetail;
        }).toList();
        product.setProductDetails(productDetails);

        product = this.productRepository.save(product);

        return this.modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        Optional<Product> products = this.productRepository.findById(productDTO.getId());
        if (products.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Mã sản phẩm"));

        if (StringCommon.isNullOrBlank(productDTO.getProductName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Tên sản phẩm"));

        Product product = products.get();
        if (!product.getProductName().equals(productDTO.getProductName())) {
            boolean isExistProductName = productRepository.existsByProductNameLike(productDTO.getProductName().trim());
            if (isExistProductName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên sản phẩm"));
            }
        }

        product = this.modelMapper.map(productDTO, Product.class);
        Product finalProduct = product;

        product.setProductImages(product.getProductImages().stream().peek(productImage -> productImage.setProduct(finalProduct)).collect(Collectors.toList()));

        product = this.productRepository.save(product);

        return this.modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        if (this.productRepository.existsById(id))
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Mã sản phẩm"));

        this.productDetailRepository.updateProductDetailStatus(id, status);

        this.productRepository.updateProductStatus(id, status);
    }
}
