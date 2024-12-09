package com.rcaspe.CarRegistry.service.converters;

import com.rcaspe.CarRegistry.domain.Car;
import com.rcaspe.CarRegistry.entity.CarEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CarConverter {

    @Autowired
    private BrandConverter brandConverter;

    public Car toCar(CarEntity entity) {
        Car car = new Car();
        car.setId(entity.getId());
        car.setBrand(brandConverter.toBrand(entity.getBrand()));
        car.setModel(entity.getModel());
        car.setYear(entity.getYear());
        car.setColour(entity.getColour());
        car.setMilleage(entity.getMilleage());
        car.setDescription(entity.getDescription());

        return car;
    }

    public CarEntity toEntity(Car entity) {
        CarEntity car = new CarEntity();
        car.setId(entity.getId());
        car.setBrand(brandConverter.toEntity(entity.getBrand()));
        car.setModel(entity.getModel());
        car.setYear(entity.getYear());
        car.setColour(entity.getColour());
        car.setMilleage(entity.getMilleage());
        car.setDescription(entity.getDescription());

        return car;
    }

}
