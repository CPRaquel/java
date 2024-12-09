package com.rcaspe.CarRegistry.controller.mapper;

import com.rcaspe.CarRegistry.controller.dtos.BrandRequest;
import com.rcaspe.CarRegistry.controller.dtos.BrandResponse;
import com.rcaspe.CarRegistry.domain.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandResponse toResponse(Brand entity) {
        BrandResponse brand = new BrandResponse();

        brand.setId(entity.getId());
        brand.setName(entity.getName());
        brand.setCountry(entity.getCountry());

        return brand;
    }

    public Brand toModel(BrandRequest entity) {
        Brand brand = new Brand();

        brand.setId(entity.getId());
        brand.setName(entity.getName());
        brand.setCountry(entity.getCountry());

        return brand;
    }


}
