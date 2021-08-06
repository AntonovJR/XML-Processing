package com.example.demo.service;

import com.example.demo.model.dto.ex1.ProductInRangeDto;
import com.example.demo.model.dto.ex1.ProductInRangeRootDto;
import com.example.demo.model.dto.seed.ProductSeedDto;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper,
                              ValidationUtil validationUtil, UserService userService,
                              CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedProducts(List<ProductSeedDto> productSeedDtos) {
        if (productRepository.count() > 0) {
            return;
        }
        productSeedDtos.stream()
                .filter(validationUtil::isValid)
                .map(productSeedRootDto -> {
                    Product product = modelMapper.map(productSeedRootDto, Product.class);

                    product.setSeller(userService.findRandomUser());

                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                        product.setBuyer(userService.findRandomUser());
                    }
                    product.setCategorySet(categoryService.findRandomCategories());
                    return product;
                })
                .forEach(productRepository::save);


    }

    @Override
    public ProductInRangeRootDto findAllProductsInRangeOrderByPrice(BigDecimal lowerBound, BigDecimal highBound) {
        ProductInRangeRootDto rootDto = new ProductInRangeRootDto();

        rootDto.setProductInRangeDtos(productRepository
                .findAllByPriceBetweenAndBuyerIdIsNullOrderByPriceDesc(lowerBound, highBound)
                .stream()
                .map(product -> {
                    ProductInRangeDto productInRangeDto = modelMapper
                            .map(product, ProductInRangeDto.class);

                    productInRangeDto.setSeller(String.format("%s %s", product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productInRangeDto;
                }).collect(Collectors.toList())
        );

        return rootDto;

    }

}
