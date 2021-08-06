package com.example.demo.model.dto.ex4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWithPartsRootDto {
    @XmlElement(name = "car")
    private List<CarsWithPartsDto> carsWithPartsDtos;

    public List<CarsWithPartsDto> getCarsWithPartsDtos() {
        return carsWithPartsDtos;
    }

    public void setCarsWithPartsDtos(List<CarsWithPartsDto> carsWithPartsDtos) {
        this.carsWithPartsDtos = carsWithPartsDtos;
    }
}
