package com.example.demo;

import com.example.demo.model.dto.ex1.OrderedCustomersRootDto;
import com.example.demo.model.dto.ex2.CarsRootPrintDto;
import com.example.demo.model.dto.ex3.LocalSuppliersRootDto;
import com.example.demo.model.dto.ex4.CarsWithPartsRootDto;
import com.example.demo.model.dto.ex5.CustomerCarsRootDto;
import com.example.demo.model.dto.ex6.SalesDiscountRootDto;
import com.example.demo.model.dto.seed.*;
import com.example.demo.service.*;
import com.example.demo.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.constants.GlobalConstants.*;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final PartSupplierService partSupplierService;
    private final SaleService saleService;
    private final BufferedReader bufferedReader;
    private final XmlParser xmlParser;

    public CommandLineRunnerImpl(CarService carService, CustomerService customerService, PartService partService,
                                 PartSupplierService partSupplierService, SaleService saleService, XmlParser xmlParser) {
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.partSupplierService = partSupplierService;
        this.saleService = saleService;
        this.xmlParser = xmlParser;

        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        while (true) {
            System.out.println("Please enter exercise number: ");
            int exNum = Integer.parseInt(bufferedReader.readLine());
            switch (exNum) {
                case 1 -> getAllCustomers();
                case 2 -> carsFromMakeToyota();
                case 3 -> getLocalSuppliers();
                case 4 -> getCarsAndParts();
                case 5 -> totalSalesByCustomer();
                case 6 -> salesWithAppliedDiscount();
                case 99 -> System.exit(0);
                default -> System.out.println("Please enter valid exercise number");
            }


        }
    }

    private void salesWithAppliedDiscount() throws JAXBException {
        SalesDiscountRootDto salesDiscountDtos = saleService.getAllSales();
        xmlParser.writeToFile(OUTPUT_FILES_PATH + CAR_SALE_FILE_NAME, salesDiscountDtos);
    }

    private void totalSalesByCustomer() throws JAXBException {
        CustomerCarsRootDto customerCarsDtos = customerService.getCustomerCars();


        xmlParser.writeToFile(OUTPUT_FILES_PATH + CUSTOMER_CARS_COSTS_FILE_NAME, customerCarsDtos);
    }

    private void getCarsAndParts() throws JAXBException {
        CarsWithPartsRootDto carsWithPartsDtos = carService.getCarsWithParts();


        xmlParser.writeToFile(OUTPUT_FILES_PATH + CARS_WITH_PARTS_FILE_NAME, carsWithPartsDtos);
    }

    private void getLocalSuppliers() throws JAXBException {
        LocalSuppliersRootDto localSuppliersDtos = partSupplierService.getLocalSuppliers();


        xmlParser.writeToFile(OUTPUT_FILES_PATH + LOCAL_SUPPLIERS_FILE_NAME, localSuppliersDtos);
    }

    private void carsFromMakeToyota() throws JAXBException {
        CarsRootPrintDto carsPrintDtos = carService.getCarByMake("Toyota");


        xmlParser.writeToFile(OUTPUT_FILES_PATH + CARS_FROM_MAKE_FILE_NAME, carsPrintDtos);

    }

    private void getAllCustomers() throws JAXBException {
        OrderedCustomersRootDto orderedCustomersRootDto = customerService.findAll();
        xmlParser.writeToFile(OUTPUT_FILES_PATH + CUSTOMER_FILE_NAME, orderedCustomersRootDto);
    }


    private void seedData() throws IOException, JAXBException {
        PartSupplierRootSeedDto partSupplierSeedDto = xmlParser
                .fromFile(RESOURCE_FILE_PATH + SUPPLIERS_FILE_NAME, PartSupplierRootSeedDto.class);
        partSupplierService.seedSuppliers(partSupplierSeedDto.getSupplierSeedDtos());

        PartsRootSeedDto partsRootSeedDto = xmlParser.fromFile(RESOURCE_FILE_PATH + PARTS_FILE_NAME, PartsRootSeedDto.class);
        partService.seedParts(partsRootSeedDto.getPartsSeedDtos());

        CarRootSeedDto carRootSeedDto = xmlParser.fromFile(RESOURCE_FILE_PATH + CARS_FILE_NAME, CarRootSeedDto.class);
        carService.seedCars(carRootSeedDto.getCarSeedDtos());

        CustomerRootSeedDto customerRootSeedDto = xmlParser.fromFile(RESOURCE_FILE_PATH + CUSTOMERS_FILE_NAME, CustomerRootSeedDto.class);
        customerService.seedCustomers(customerRootSeedDto.getCustomerSeedDtoList());


        saleService.seedSales();


    }
}
