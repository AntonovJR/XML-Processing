package com.example.demo.service;

import com.example.demo.model.dto.ex2.UserSoldDto;
import com.example.demo.model.dto.ex2.UserSoldRootDto;
import com.example.demo.model.dto.ex3.ProductByCategoryRootDto;
import com.example.demo.model.dto.ex3.ProductsByCategoryDto;
import com.example.demo.model.dto.seed.CategorySeedDto;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categorySeedRootDto){
        if (categoryRepository.count() > 0) {
            return;
        }
        categorySeedRootDto.stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categorySet = new HashSet<>();
        int categoryCount = ThreadLocalRandom.current().nextInt(1, 4);
        long totalCategoriesCount = categoryRepository.count();

        for (int i = 0; i < categoryCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, totalCategoriesCount + 1);
            categorySet.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categorySet;
    }

    @Override
    public ProductByCategoryRootDto productsDetailsByCategory() {
        ProductByCategoryRootDto rootDto = new ProductByCategoryRootDto();

        rootDto.setProductsByCategoryDtoList(categoryRepository.getCategoriesByProductsCount().stream()
                .map(category -> {
                    ProductsByCategoryDto categoryByProductsDto = modelMapper.map(category, ProductsByCategoryDto.class);
                    setStatistics(category, categoryByProductsDto);
                    return categoryByProductsDto;
                }).collect(Collectors.toList()));

        return rootDto;
    }
    private void setStatistics(Category category, ProductsByCategoryDto categoryByProductsDto) {
        int count = category.getProducts().size();
        BigDecimal averagePrice = BigDecimal.ZERO;
        BigDecimal totalRevenue = category.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (count>0){
            averagePrice = totalRevenue.divide(BigDecimal.valueOf(count), RoundingMode.FLOOR);}
        categoryByProductsDto.setProductsCount(count);
        categoryByProductsDto.setTotalRevenue(totalRevenue);
        categoryByProductsDto.setAveragePrice(averagePrice);


    }

}
