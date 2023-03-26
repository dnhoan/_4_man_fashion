package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.ProductDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.*;
import com.example._4_man_fashion.repositories.ColorRepository;
import com.example._4_man_fashion.repositories.ProductDetailRepository;
import com.example._4_man_fashion.repositories.ProductRepository;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Transactional
    public PageDTO<ProductDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Product> page = this.productRepository.getProductByName(pageable, status, StringCommon.getLikeCondition(search));
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

    @Transactional
    public List<Product> getListProduct() {
        List<Product> lstProduct = this.productRepository.findAll();
        return lstProduct;
    }

    @Override
    @Transactional
    public ProductDTO getById(Integer id) {
        Optional<Product> product = this.productRepository.findById(id);

        ProductDTO productDTO =  product.map(value -> this.modelMapper.map(value, ProductDTO.class)).orElseThrow(() -> {
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND_OR_INACTIVE.format("Sản phẩm "));
        });

        List<Color> colors = this.colorRepository.getColorsByProductId(id);
        List<Size> sizes = this.sizeRepository.getSizesByProductId(id);
        productDTO.setColors(colors);
        productDTO.setSizes(sizes);

        return  productDTO;
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
        return getProductDTO(productDTO, product);
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
        product.setMtime(LocalDateTime.now());

        return getProductDTO(productDTO, product);
    }

    private ProductDTO getProductDTO(ProductDTO productDTO, Product product) {
        Product finalProduct = product;

        product.setProductImages(product.getProductImages().stream().peek(productImage -> productImage.setProduct(finalProduct)).collect(Collectors.toList()));

        List<ProductDetail> productDetails = productDTO.getProductDetails().stream().map(productDetailDTO -> {
            ProductDetail productDetail = this.modelMapper.map(productDetailDTO, ProductDetail.class);
            productDetail.setProduct(finalProduct);
            return productDetail;
        }).toList();
        product.setProductDetails(productDetails);
        product.setCategoryName(product.getCategory().getCategoryName());
        product.setModelName(product.getModel().getModelsName());
        product.setMaterialName(product.getMaterial().getMaterialName());
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
