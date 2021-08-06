package com.example.demo.service;

import com.example.demo.model.dto.ex6.SalesDiscountRootDto;


public interface SaleService {
    void seedSales();

    SalesDiscountRootDto getAllSales();
}
