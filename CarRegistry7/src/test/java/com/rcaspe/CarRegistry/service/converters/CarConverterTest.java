package com.rcaspe.CarRegistry.service.converters;

import com.rcaspe.CarRegistry.domain.Brand;
import com.rcaspe.CarRegistry.domain.Car;
import com.rcaspe.CarRegistry.entity.BrandEntity;
import com.rcaspe.CarRegistry.entity.CarEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarConverterTest {

    @InjectMocks
    private CarConverter carConverter;

    @Mock
    private BrandConverter brandConverter;

    @Test
    void toCar_test() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Seat");

        CarEntity entity = new CarEntity();
        entity.setId(1);
        entity.setYear(2000);
        entity.setMilleage(2000);
        entity.setModel("Arona");
        entity.setBrand(brandEntity);
        entity.setColour("red");
        entity.setDescription("desc");

        Brand brand = new Brand();
        brand.setName("Seat");

        Car car = new Car();

        car.setId(1);
        car.setYear(2000);
        car.setMilleage(2000);
        car.setModel("Arona");
        car.setBrand(brand);
        car.setColour("red");
        car.setDescription("desc");

        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Car result = carConverter.toCar(entity);
        assertEquals(result.getId(), car.getId());
        assertEquals(result.getYear(), car.getYear());
        assertEquals(result.getMilleage(), car.getMilleage());
        assertEquals(result.getModel(), car.getModel());
        assertEquals(result.getBrand(), car.getBrand());
        assertEquals(result.getColour(), car.getColour());
        assertEquals(result.getDescription(), car.getDescription());

    }

}
