package com.example.demo.service;

import com.example.demo.model.dto.ex3.ProductByCategoryRootDto;
import com.example.demo.model.dto.seed.CategorySeedDto;
import com.example.demo.model.entity.Category;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categorySeedRootDto) throws IOException, JAXBException;
    Set<Category> findRandomCategories();

    ProductByCategoryRootDto productsDetailsByCategory();
}
