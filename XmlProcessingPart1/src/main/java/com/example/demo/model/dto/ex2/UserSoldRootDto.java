package com.example.demo.model.dto.ex2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldRootDto {
    @XmlElement(name = "user")
    private List<UserSoldDto> userSoldDtos;


    public List<UserSoldDto> getUserSoldDtos() {
        return userSoldDtos;
    }

    public void setUserSoldDtos(List<UserSoldDto> userSoldDtos) {
        this.userSoldDtos = userSoldDtos;
    }
}
