package com.example.demo.model.dto.ex4;


import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWithPartsDto {
    @XmlElement(name = "car")
    private CarPartsDto car;
    @XmlElement(name = "part")
    @XmlElementWrapper(name = "parts")
    private List<PartsPrintDto> parts;


    public CarPartsDto getCar() {
        return car;
    }

    public void setCar(CarPartsDto car) {
        this.car = car;
    }

    public List<PartsPrintDto> getParts() {
        return parts;
    }

    public void setParts(List<PartsPrintDto> parts) {
        this.parts = parts;
    }
}
