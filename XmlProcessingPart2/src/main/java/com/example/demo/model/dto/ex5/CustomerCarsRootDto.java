package com.example.demo.model.dto.ex5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerCarsRootDto {
    @XmlElement(name = "customer")
    private List<CustomerCarsDto> customerCarsDtos;

    public List<CustomerCarsDto> getCustomerCarsDtos() {
        return customerCarsDtos;
    }

    public void setCustomerCarsDtos(List<CustomerCarsDto> customerCarsDtos) {
        this.customerCarsDtos = customerCarsDtos;
    }
}
