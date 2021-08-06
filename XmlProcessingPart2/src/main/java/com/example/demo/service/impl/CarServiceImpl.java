package com.example.demo.service.impl;

import com.example.demo.model.dto.ex2.CarsRootPrintDto;
import com.example.demo.model.dto.ex4.CarPartsDto;
import com.example.demo.model.dto.ex4.CarsWithPartsDto;
import com.example.demo.model.dto.ex4.CarsWithPartsRootDto;
import com.example.demo.model.dto.ex4.PartsPrintDto;
import com.example.demo.model.dto.ex2.CarsPrintDto;
import com.example.demo.model.dto.seed.CarSeedDto;
import com.example.demo.model.entity.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import com.example.demo.service.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final PartService partService;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.partService = partService;
    }

    @Override
    public void seedCars(List<CarSeedDto> carSeedDtos) {
        if (this.carRepository.count() > 0) {
            return;
        }

        carSeedDtos.stream()
                .map(carSeedDto -> {
                    Car car = this.modelMapper.map(carSeedDto, Car.class);
                    car.setPartList(this.partService.getRandomPartsList());
                    return car;
                })
                .forEach(this.carRepository::save);


    }

    @Override
    public Car getRandomCar() {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1);

        return this.carRepository.findById(randomId).orElse(null);
    }

    @Override
    public CarsRootPrintDto getCarByMake(String carBrand) {
        CarsRootPrintDto carsRootPrintDto = new CarsRootPrintDto();

        carsRootPrintDto.setCarsPrintDtos(this.carRepository.findAllByMakeOrderByModelAndOrderByTravelDistanceDesc(carBrand)
                .stream()
                .map(car -> modelMapper.map(car, CarsPrintDto.class))
                .collect(Collectors.toList()));

        return carsRootPrintDto;
    }

    @Override
    public CarsWithPartsRootDto getCarsWithParts() {
        CarsWithPartsRootDto carsWithPartsRootDto = new CarsWithPartsRootDto();
        carsWithPartsRootDto.setCarsWithPartsDtos(carRepository.findAll().stream().map(car -> {
            CarsWithPartsDto carsWithPartsDto = new CarsWithPartsDto();
            carsWithPartsDto.setCar(modelMapper.map(car, CarPartsDto.class));

            carsWithPartsDto.setParts(car.getPartList()
                    .stream()
                    .map(part -> modelMapper.map(part, PartsPrintDto.class))
                    .collect(Collectors.toList()));
            return carsWithPartsDto;
        })
                .collect(Collectors.toList()));

        return carsWithPartsRootDto;
    }


}
