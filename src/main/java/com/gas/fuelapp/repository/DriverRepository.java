package com.gas.fuelapp.repository;

import com.gas.fuelapp.entity.Driver;
import com.gas.fuelapp.entity.FuelConsumption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {

    Collection<Driver> findByDriverId(String fuelName);
}