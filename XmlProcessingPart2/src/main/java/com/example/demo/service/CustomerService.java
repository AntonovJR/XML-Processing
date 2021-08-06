package com.example.demo.service;

import com.example.demo.model.dto.ex1.OrderedCustomersRootDto;
import com.example.demo.model.dto.ex5.CustomerCarsRootDto;
import com.example.demo.model.dto.seed.CustomerSeedDto;
import com.example.demo.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomers(List<CustomerSeedDto> customerSeedDtoList);

    long getCarsCount();

    Customer getCustomerById(long i);

    OrderedCustomersRootDto findAll();

    CustomerCarsRootDto getCustomerCars();
}
