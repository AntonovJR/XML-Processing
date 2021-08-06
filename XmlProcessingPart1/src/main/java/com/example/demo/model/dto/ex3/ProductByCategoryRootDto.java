package com.example.demo.model.dto.ex3;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductByCategoryRootDto {
    @XmlElement(name = "category")
    private List<ProductsByCategoryDto> productsByCategoryDtoList;

    public List<ProductsByCategoryDto> getProductsByCategoryDtoList() {
        return productsByCategoryDtoList;
    }

    public void setProductsByCategoryDtoList(List<ProductsByCategoryDto> productsByCategoryDtoList) {
        this.productsByCategoryDtoList = productsByCategoryDtoList;
    }
}
