package com.example.demo.model.dto.ex6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesDiscountRootDto {

    @XmlElement(name = "sale")
    private List<SalesDiscountDto> salesDiscountDtoList;

    public List<SalesDiscountDto> getSalesDiscountDtoList() {
        return salesDiscountDtoList;
    }

    public void setSalesDiscountDtoList(List<SalesDiscountDto> salesDiscountDtoList) {
        this.salesDiscountDtoList = salesDiscountDtoList;
    }
}
