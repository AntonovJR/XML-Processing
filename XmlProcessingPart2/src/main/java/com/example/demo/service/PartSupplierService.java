package com.example.demo.service;

import com.example.demo.model.dto.ex3.LocalSuppliersRootDto;
import com.example.demo.model.dto.seed.PartSupplierSeedDto;
import com.example.demo.model.entity.PartSupplier;

import java.util.List;

public interface PartSupplierService {
    void seedSuppliers(List<PartSupplierSeedDto> supplierSeedDtos) ;

    PartSupplier findRandomSupplier();

    LocalSuppliersRootDto getLocalSuppliers();
}
