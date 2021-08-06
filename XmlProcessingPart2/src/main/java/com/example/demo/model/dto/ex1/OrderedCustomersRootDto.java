package com.example.demo.model.dto.ex1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderedCustomersRootDto {
    @XmlElement(name = "customer")
    private List<OrderedCustomerDto> orderedCustomerDtos;

    public List<OrderedCustomerDto> getOrderedCustomerDtos() {
        return orderedCustomerDtos;
    }

    public void setOrderedCustomerDtos(List<OrderedCustomerDto> orderedCustomerDtos) {
        this.orderedCustomerDtos = orderedCustomerDtos;
    }
}
