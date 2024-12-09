package com.rcaspe.CarRegistry.repository;

import com.rcaspe.CarRegistry.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarEntity,Integer> {


}
