package com.rcaspe.CarRegistry.service.impl;

import com.rcaspe.CarRegistry.controller.dtos.CarRequest;
import com.rcaspe.CarRegistry.controller.dtos.CarResponse;
import com.rcaspe.CarRegistry.domain.Car;
import com.rcaspe.CarRegistry.entity.CarEntity;
import com.rcaspe.CarRegistry.repository.BrandRepository;
import com.rcaspe.CarRegistry.repository.CarRepository;
import com.rcaspe.CarRegistry.service.CarService;
import com.rcaspe.CarRegistry.service.converters.CarConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarConverter carConverter;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Car getCarById(int id) {
        log.info("Retrieving car with id {}", id);
        Optional<CarEntity> carOptional = carRepository.findById(id);

        if(carOptional.isPresent()) {
            return carConverter.toCar(carOptional.get());
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Deleting car with id {}", id);
        carRepository.deleteById(id);
    }

    @Override
    public Car updateByid(Integer id, Car carRequest) {

        log.info("Updating car with id {}", id);
        Optional<CarEntity> carOptional = carRepository.findById(id);

        if(carOptional.isPresent()) {
            CarEntity entity = carConverter.toEntity(carRequest);
            entity.setId(id);

            return carConverter.toCar(carRepository.save(entity));
        }
        return null;

    }

    @Override
    public Car saveCar(Car carRequest) {
        log.info("Adding car to Database...");
        CarEntity entity = carConverter.toEntity(carRequest);

        return carConverter.toCar(carRepository.save(entity));
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> getAllCars() {
        List<CarEntity> carsList = carRepository.findAll();

        List<Car> cars = new ArrayList<>();
        carsList.forEach(car -> {
            cars.add(carConverter.toCar(car));
        });
        return CompletableFuture.completedFuture(cars);
    }

    @Override
    public String carsCsv() {
        List<CarEntity> carList = carRepository.findAll();
        StringBuilder csvContent = new StringBuilder();

        for (CarEntity car : carList) {
            csvContent.append(car.getBrand()).append(",")
                    .append(car.getModel()).append(",")
                    .append(car.getColour()).append(",")
                    .append(car.getDescription()).append(",")
                    .append(car.getYear()).append(",")
                    .append(car.getPrice()).append(",")
                    .append(car.getMilleage()).append(",")
                    .append("\n");
        }

        return csvContent.toString();
    }

    @Override
    public void uploadCars(MultipartFile file) {

        List<CarEntity> carList = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){

            Iterable<CSVRecord> csvRecord = csvParser.getRecords();

            for (CSVRecord record : csvRecord) {
                CarEntity car = new CarEntity();

                car.setBrand(brandRepository.findByName(record.get("brand")));
                car.setModel(record.get("model"));
                car.setDescription(record.get("description"));
                car.setYear(Integer.parseInt(record.get("year")));
                car.setColour(record.get("colour"));
                car.setMilleage(Integer.parseInt(record.get("milleage")));

                carList.add(car);
            }
            carList = carRepository.saveAll(carList);
        }
        catch (Exception e) {
            log.error("Failed to load users");
            throw new RuntimeException("Failed to load users");
        }

    }


}
