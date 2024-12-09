package com.rcaspe.CarRegistry.service;

import com.rcaspe.CarRegistry.domain.Car;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {

    Car getCarById(int id);

    void deleteById(Integer id);

    Car updateByid(Integer id, Car carRequest);

    Car saveCar(Car carRequest);

    CompletableFuture<List<Car>> getAllCars();

    String carsCsv();

    void uploadCars(MultipartFile file);

}
