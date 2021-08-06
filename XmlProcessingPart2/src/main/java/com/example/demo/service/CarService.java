package com.example.demo.service;

import com.example.demo.model.dto.ex2.CarsRootPrintDto;
import com.example.demo.model.dto.ex4.CarsWithPartsRootDto;
import com.example.demo.model.dto.seed.CarSeedDto;
import com.example.demo.model.entity.Car;

import java.util.List;

public interface CarService {
    void seedCars(List<CarSeedDto> carSeedDtos);

    Car getRandomCar();

   CarsRootPrintDto getCarByMake(String carBrand);

    CarsWithPartsRootDto getCarsWithParts();
}
