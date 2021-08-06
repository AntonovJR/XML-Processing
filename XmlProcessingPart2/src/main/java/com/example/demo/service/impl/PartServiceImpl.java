package com.example.demo.service.impl;

import com.example.demo.model.dto.seed.PartsSeedDto;
import com.example.demo.model.entity.Part;
import com.example.demo.repository.PartRepository;
import com.example.demo.service.PartService;
import com.example.demo.service.PartSupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final PartSupplierService partSupplierService;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper,
                           PartSupplierService partSupplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.partSupplierService = partSupplierService;
    }

    @Override
    public void seedParts(List<PartsSeedDto> partsSeedDtos) {
        if ( this.partRepository.count() > 0) {
            return;
        }

        partsSeedDtos.stream()
                .map(productSeedDto -> {
                    Part part =  this.modelMapper.map(productSeedDto, Part.class);
                    part.setPartSupplier( this.partSupplierService.findRandomSupplier());
                    return part;
                })
                .forEach( this.partRepository::save);
    }

    @Override
    public List<Part> getRandomPartsList() {
        List<Part> partList = new ArrayList<>();
        int randomSize = ThreadLocalRandom.current().nextInt(3, 6);
        long repoSize = this.partRepository.count();

        for (int i = 0; i < randomSize ; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, repoSize+1);
            partList.add(this.partRepository.findById(randomId).orElse(null));
        }
        return partList;
    }
}
