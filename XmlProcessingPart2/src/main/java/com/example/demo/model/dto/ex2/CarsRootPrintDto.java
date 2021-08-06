package com.example.demo.model.dto.ex2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsRootPrintDto {
    @XmlElement(name = "car")
    private List<CarsPrintDto> carsPrintDtos;

    public List<CarsPrintDto> getCarsPrintDtos() {
        return carsPrintDtos;
    }

    public void setCarsPrintDtos(List<CarsPrintDto> carsPrintDtos) {
        this.carsPrintDtos = carsPrintDtos;
    }
}
