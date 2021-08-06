package com.example.demo.model.dto.seed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsRootSeedDto {

    @XmlElement(name = "part")
    private List<PartsSeedDto> partsSeedDtos;

    public List<PartsSeedDto> getPartsSeedDtos() {
        return partsSeedDtos;
    }

    public void setPartsSeedDtos(List<PartsSeedDto> partsSeedDtos) {
        this.partsSeedDtos = partsSeedDtos;
    }
}
