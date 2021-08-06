package com.example.demo.service.impl;

import com.example.demo.model.dto.ex3.LocalSuppliersDto;

import com.example.demo.model.dto.ex3.LocalSuppliersRootDto;
import com.example.demo.model.dto.seed.PartSupplierSeedDto;
import com.example.demo.model.entity.PartSupplier;
import com.example.demo.repository.PartSupplierRepository;
import com.example.demo.service.PartSupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class PartSupplierServiceImpl implements PartSupplierService {
    private final PartSupplierRepository partSupplierRepository;
    private final ModelMapper modelMapper;

    public PartSupplierServiceImpl(PartSupplierRepository partSupplierRepository, ModelMapper modelMapper) {
        this.partSupplierRepository = partSupplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSuppliers(List<PartSupplierSeedDto> supplierSeedDtos) {
        if (partSupplierRepository.count() > 0) {
            return;
        }

        supplierSeedDtos.stream()
                .map(partSupplierSeedDto -> modelMapper.map(partSupplierSeedDto, PartSupplier.class))
                .forEach(partSupplierRepository::save);


    }

    @Override
    public PartSupplier findRandomSupplier() {
        long randomId = ThreadLocalRandom.current().nextLong(1, partSupplierRepository.count() + 1);

        return partSupplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public LocalSuppliersRootDto getLocalSuppliers() {
        LocalSuppliersRootDto localSuppliersRootDto = new LocalSuppliersRootDto();

        localSuppliersRootDto.setLocalSuppliersDtos(partSupplierRepository.findAllByIsImporter(false)
                .stream()
                .map(partSupplier -> {
                    LocalSuppliersDto localSuppliersDto = modelMapper.map(partSupplier, LocalSuppliersDto.class);
                    localSuppliersDto.setPartsCount(partSupplier.getPartList().size());
                    return localSuppliersDto;
                })
                .collect(Collectors.toList()));

        return localSuppliersRootDto;

    }
}
