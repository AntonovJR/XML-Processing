package com.example.demo.service;

import com.example.demo.model.dto.ex2.UserSoldRootDto;
import com.example.demo.model.dto.seed.UserSeedDto;
import com.example.demo.model.dto.ex4.CountOfSellersDto;
import com.example.demo.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers(List<UserSeedDto> userSeedDtos) throws IOException;
    User findRandomUser();

    UserSoldRootDto findAllUsersWithMoreThanOneSoldProducts();

     CountOfSellersDto findAllUsersWithSales();
}

