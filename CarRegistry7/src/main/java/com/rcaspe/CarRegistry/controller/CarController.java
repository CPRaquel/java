package com.rcaspe.CarRegistry.controller;

import com.rcaspe.CarRegistry.controller.dtos.CarRequest;
import com.rcaspe.CarRegistry.controller.dtos.CarResponse;
import com.rcaspe.CarRegistry.controller.mapper.CarMapper;
import com.rcaspe.CarRegistry.domain.Car;
import com.rcaspe.CarRegistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cars")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'VENDOR')")
    public ResponseEntity<?> getCar(@PathVariable Integer id) {

        log.info("Retriving Car info");
        try {
            return ResponseEntity.ok(carService.getCarById(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('CLIENT', 'VENDOR')")
    public CompletableFuture<?> getCars() {

        log.info("Retriving Car info");
        try {
            CompletableFuture<List<Car>> cars = carService.getAllCars();
            List<CarResponse> response = new ArrayList<>();
            cars.get().forEach(car -> {
                response.add(carMapper.toResponse(car));
            });
            return CompletableFuture.completedFuture(response);
        }
        catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound());
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteCarById(@PathVariable Integer id) {

        log.info("Retriving Car info");
        try {
            carService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest) {
        try {
            CarResponse response = carMapper.toResponse(carService.saveCar(carMapper.toModel(carRequest)));
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('VENDOR')")
    public ResponseEntity<?> updateCarById(@PathVariable Integer id, @RequestBody CarRequest carRequest) {
        try {
            carService.updateByid(id, carMapper.toModel(carRequest));
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/downloadCars")
    @PreAuthorize("hasAnyRole('CLIENT', 'VENDOR')")
    public ResponseEntity<?> downloadCars() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "users.csv");

        byte[] csvBytes = carService.carsCsv().getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadCars", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('CLIENT', 'VENDOR')")
    public ResponseEntity<String> uploadCsv(@RequestParam(value = "file")MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            log.error("The file it's empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (file.getOriginalFilename().contains(".csv")) {
            carService.uploadCars(file);
            return ResponseEntity.ok("File successfully uploaded");
        }

        log.error("The file it's not a CSV");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The file it's not a CSV");
    }
}
