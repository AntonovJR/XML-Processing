package com.example.demo.service;

import com.example.demo.model.dto.ex1.ProductInRangeRootDto;
import com.example.demo.model.dto.seed.ProductSeedDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(List<ProductSeedDto> productSeedDtos) throws IOException;

    ProductInRangeRootDto findAllProductsInRangeOrderByPrice(BigDecimal lowerBound, BigDecimal highBound);

}
