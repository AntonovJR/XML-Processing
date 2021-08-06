package com.example.demo.service;

import com.example.demo.model.dto.seed.PartsSeedDto;
import com.example.demo.model.entity.Part;

import java.util.List;

public interface PartService {
    void seedParts(List<PartsSeedDto> partsSeedDtos);

    List<Part> getRandomPartsList();
}
