package com.example.demo.model.dto.ex4;


import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsDto {
    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "product")
    private List<ProductsDetailsDto> soldProducts;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductsDetailsDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductsDetailsDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
