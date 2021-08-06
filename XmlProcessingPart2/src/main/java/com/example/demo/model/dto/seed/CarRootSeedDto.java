package com.example.demo.model.dto.seed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarRootSeedDto {
    @XmlElement(name = "car")
    private List<CarSeedDto> carSeedDtos;

    public List<CarSeedDto> getCarSeedDtos() {
        return carSeedDtos;
    }

    public void setCarSeedDtos(List<CarSeedDto> carSeedDtos) {
        this.carSeedDtos = carSeedDtos;
    }
}
