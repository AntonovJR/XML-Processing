package com.example.demo.service.impl;


import com.example.demo.model.dto.ex1.OrderedCustomerDto;
import com.example.demo.model.dto.ex1.OrderedCustomersRootDto;
import com.example.demo.model.dto.ex5.CustomerCarsDto;
import com.example.demo.model.dto.ex5.CustomerCarsRootDto;
import com.example.demo.model.dto.seed.CustomerSeedDto;
import com.example.demo.model.entity.Car;
import com.example.demo.model.entity.Customer;
import com.example.demo.model.entity.Part;
import com.example.demo.model.entity.Sale;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;


    }

    @Override
    public void seedCustomers(List<CustomerSeedDto> customerSeedDtoList) {
        if (this.customerRepository.count() > 0) {
            return;
        }
        customerSeedDtoList.stream()
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    customer.setBirthDate(LocalDateTime.parse(customerSeedDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    return customer;
                })
                .forEach(this.customerRepository::save);
    }

    @Override
    public long getCarsCount() {
        return this.customerRepository.count();
    }

    @Override
    public Customer getCustomerById(long i) {
        return this.customerRepository.findById(i).orElse(null);
    }

    @Override
    public OrderedCustomersRootDto findAll() {
OrderedCustomersRootDto orderedCustomersRootDto = new OrderedCustomersRootDto();

        orderedCustomersRootDto.setOrderedCustomerDtos(customerRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getBirthDate).thenComparing(Customer::getYoungDriver))
                .map(customer -> modelMapper.map(customer, OrderedCustomerDto.class))
                .collect(Collectors.toList()));


        return  orderedCustomersRootDto;
    }

    @Override
    public CustomerCarsRootDto getCustomerCars() {
        CustomerCarsRootDto customerCarsRootDto = new CustomerCarsRootDto();

        customerCarsRootDto.setCustomerCarsDtos(customerRepository.getAllBySaleListBiggerThanOne()
                .stream()
                .map(customer -> {
                    CustomerCarsDto customerCarsDto = new CustomerCarsDto();
                    customerCarsDto.setFullName(customer.getName());
                    customerCarsDto.setBoughtCars(customer.getSaleList().size());
                    List<Car> cars = customer.getSaleList().stream().map(Sale::getCar).collect(Collectors.toList());
                    List<BigDecimal> discount = customer.getSaleList().stream().map(Sale::getDiscountPercentage)
                            .collect(Collectors.toList());

                    BigDecimal spendMoney = BigDecimal.valueOf(0);
                    for (Car car : cars) {
                        List<Part> parts = car.getPartList();
                        for (Part part : parts) {
                            spendMoney = spendMoney.add(part.getPrice());
                        }
                    }
                    spendMoney = spendMoney.multiply(BigDecimal.valueOf(1L).subtract(discount.get(0)
                            .divide(BigDecimal.valueOf(100L), RoundingMode.UNNECESSARY)));
                    customerCarsDto.setSpentMoney(spendMoney);
                    return customerCarsDto;
                })
                .collect(Collectors.toList()));

        return customerCarsRootDto;
    }
}
