package com.rcaspe.CarRegistry.controller.mapper;

import com.rcaspe.CarRegistry.controller.dtos.CarRequest;
import com.rcaspe.CarRegistry.controller.dtos.CarResponse;
import com.rcaspe.CarRegistry.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    @Autowired
    private BrandMapper brandMapper;

    public CarResponse toResponse(Car entity) {
        CarResponse car = new CarResponse();
        car.setId(entity.getId());
        car.setBrand(brandMapper.toResponse(entity.getBrand()));
        car.setModel(entity.getModel());
        car.setYear(entity.getYear());
        car.setColour(entity.getColour());
        car.setMilleage(entity.getMilleage());
        car.setDescription(entity.getDescription());

        return car;
    }

    public Car toModel(CarRequest entity) {
        Car car = new Car();
        car.setId(entity.getId());
        car.setBrand(brandMapper.toModel(entity.getBrand()));
        car.setModel(entity.getModel());
        car.setYear(entity.getYear());
        car.setColour(entity.getColour());
        car.setMilleage(entity.getMilleage());
        car.setDescription(entity.getDescription());

        return car;
    }
}
