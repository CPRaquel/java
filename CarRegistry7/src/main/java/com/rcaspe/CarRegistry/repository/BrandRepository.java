package com.rcaspe.CarRegistry.repository;

import com.rcaspe.CarRegistry.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    BrandEntity findByName(String name);

}
