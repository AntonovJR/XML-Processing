package com.example.demo;


import com.example.demo.model.dto.ex1.ProductInRangeRootDto;
import com.example.demo.model.dto.ex2.UserSoldRootDto;
import com.example.demo.model.dto.ex3.ProductByCategoryRootDto;
import com.example.demo.model.dto.seed.CategorySeedRootDto;
import com.example.demo.model.dto.seed.ProductSeedRootDto;
import com.example.demo.model.dto.seed.UserSeedRootDto;
import com.example.demo.model.dto.ex4.CountOfSellersDto;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import static com.example.demo.constants.GlobalConstants.*;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final XmlParser xmlParser;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService,
                                 ProductService productService, XmlParser xmlParser) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.xmlParser = xmlParser;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        while (true) {
            System.out.println("number 99 will exit the program");
            System.out.println("Enter exercise num: ");
            int exNum = Integer.parseInt(bufferedReader.readLine());

            switch (exNum) {
                case 1 -> productsInRange();
                case 2 -> soldProducts();
                case 3 -> categoriesByProductsCount();
                case 4 -> usersAndProducts();
                case 99 -> System.exit(0);
                default -> System.out.println("Enter valid exercise number");
            }

        }
    }

  private void usersAndProducts() throws JAXBException {
        CountOfSellersDto countOfSellersDto = this.userService.findAllUsersWithSales();

      xmlParser.writeToFile(OUTPUT_FILES_PATH + USER_SOLD_PRODUCTS_DETAILS_FILE_NAME, countOfSellersDto);
    }

    private void categoriesByProductsCount() throws IOException, JAXBException {
        ProductByCategoryRootDto productByCategoryCountDto = categoryService.productsDetailsByCategory();

        xmlParser.writeToFile(OUTPUT_FILES_PATH + PRODUCTS_DETAILS_BY_CATEGORY_FILE_NAME, productByCategoryCountDto);
    }

    private void soldProducts() throws JAXBException {
        UserSoldRootDto userSoldRootDto =
                userService.findAllUsersWithMoreThanOneSoldProducts();

        xmlParser.writeToFile(OUTPUT_FILES_PATH + USER_SOLD_PRODUCTS_FILE_NAME, userSoldRootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductInRangeRootDto products = productService.findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        xmlParser.writeToFile(OUTPUT_FILES_PATH + PRODUCT_IN_RANGE_FILE_NAME, products);
    }


    private void seedData() throws IOException, JAXBException {
        CategorySeedRootDto categorySeedRootDto = xmlParser
                .fromFile(RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME, CategorySeedRootDto.class);
        categoryService.seedCategories(categorySeedRootDto.getCategories());

        UserSeedRootDto userSeedRootDtoDto = xmlParser
                .fromFile(RESOURCE_FILE_PATH + USERS_FILE_NAME, UserSeedRootDto.class);
        userService.seedUsers(userSeedRootDtoDto.getUserSeedDtos());

        ProductSeedRootDto productSeedRootDto = xmlParser
                .fromFile(RESOURCE_FILE_PATH + PRODUCTS_FILE_NAME, ProductSeedRootDto.class);
        productService.seedProducts(productSeedRootDto.getProductSeedDtos());

    }
}
