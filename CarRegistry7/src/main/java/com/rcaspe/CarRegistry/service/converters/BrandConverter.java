package com.rcaspe.CarRegistry.service.converters;

import com.rcaspe.CarRegistry.domain.Brand;
import com.rcaspe.CarRegistry.entity.BrandEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component
public class BrandConverter {

    public Brand toBrand(BrandEntity entity) {
        Brand brand = new Brand();

        brand.setId(entity.getId());
        brand.setName(entity.getName());
        brand.setCountry(entity.getCountry());

        return brand;
    }

    public BrandEntity toEntity(Brand entity) {
        BrandEntity brand = new BrandEntity();

        brand.setId(entity.getId());
        brand.setName(entity.getName());
        brand.setCountry(entity.getCountry());

        return brand;
    }


}
