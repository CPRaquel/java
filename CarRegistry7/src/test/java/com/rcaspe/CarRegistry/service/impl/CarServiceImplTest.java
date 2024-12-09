package com.rcaspe.CarRegistry.service.impl;

import com.rcaspe.CarRegistry.domain.Car;
import com.rcaspe.CarRegistry.entity.CarEntity;
import com.rcaspe.CarRegistry.repository.CarRepository;
import com.rcaspe.CarRegistry.service.converters.CarConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter carConverter;

    @Test
    void getCarById_test() {
        // Given
        CarEntity entity = new CarEntity();
        entity.setId(1);

        Car car = new Car();
        car.setId(1);

        // When
        when(carRepository.findById(1)).thenReturn(Optional.of(entity));
        when(carConverter.toCar(entity)).thenReturn(car);

        // Then
        Car result = carService.getCarById(1);
        assertEquals(result, car);


    }

    @Test
    void getCarById_test_ko() {
        // Given

        // When
        when(carRepository.findById(1)).thenReturn(Optional.empty());

        // Then
        Car result = carService.getCarById(1);
        assertNull(result);


    }


}
