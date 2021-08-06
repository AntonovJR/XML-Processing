package com.example.demo.service;

import com.example.demo.model.dto.ex2.*;
import com.example.demo.model.dto.seed.UserSeedDto;
import com.example.demo.model.dto.ex4.CountOfSellersDto;
import com.example.demo.model.dto.ex4.ProductsDetailsDto;
import com.example.demo.model.dto.ex4.SoldProductsDto;
import com.example.demo.model.dto.ex4.UsersDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers(List<UserSeedDto> userSeedDtos) {
        if (userRepository.count() == 0) {
            userSeedDtos.stream()
                    .filter(validationUtil::isValid)
                    .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                    .forEach(userRepository::save);
        }
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);
        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UserSoldRootDto findAllUsersWithMoreThanOneSoldProducts() {
        UserSoldRootDto rootDto = new UserSoldRootDto();

        rootDto.setUserSoldDtos(userRepository.findAllUsersWithMoreThanOneSoldProductsOrderByLastNameThenFirstName()
                .stream()
                .filter(validationUtil::isValid)
                .map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList()));

        return rootDto;
    }
    @Override
    public CountOfSellersDto findAllUsersWithSales() {
       List<User> users = this.userRepository.findAllUsersWithMoreThanOneSoldProductsOrderBySoldProducts();
        CountOfSellersDto usersCountDto = new CountOfSellersDto();
        List<UsersDto> usersAndProductsDtos = users.stream().map(user -> {
                    UsersDto userDto = modelMapper.map(user, UsersDto.class);

                    List<ProductsDetailsDto> productDtos = user.getSoldProducts().stream()
                            .map(product -> modelMapper.map(product, ProductsDetailsDto.class))
                            .collect(Collectors.toList());

                    SoldProductsDto products = new SoldProductsDto();
                    products.setSoldProducts(productDtos);
                    products.setCount(productDtos.size());
                    userDto.setSoldProducts(products);

                    return userDto;
                }
        ).collect(Collectors.toList());
        usersCountDto.setUsers(usersAndProductsDtos);
        usersCountDto.setUsersCount(users.size());
        return usersCountDto;
    }
}
