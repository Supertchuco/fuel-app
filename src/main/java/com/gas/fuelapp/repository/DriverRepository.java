package com.gas.fuelapp.repository;

import com.gas.fuelapp.entity.Driver;
import com.gas.fuelapp.entity.FuelConsumption;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface DriverRepository extends CrudRepository<Driver, Long> {

    Collection<FuelConsumption> findByDriverId(String fuelName);
}