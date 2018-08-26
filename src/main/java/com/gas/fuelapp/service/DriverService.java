package com.gas.fuelapp.service;

import com.gas.fuelapp.entity.Driver;
import com.gas.fuelapp.entity.FuelConsumption;
import com.gas.fuelapp.repository.DriverRepository;
import com.gas.fuelapp.vo.SaveFuelConsumptionPayloadVO;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    public boolean existDriverIdOnDatabase(String driverId){
        return (driverRepository.findByDriverId(driverId).isEmpty())? false : true;
    }

    public Driver saveDriver(Driver driver){
        return driverRepository.save(driver);
    }

    public Collection<Driver> saveDriver(final SaveFuelConsumptionPayloadVO payload) {
        List<Driver> drives = payload.getFuelConsumptions().stream()
                .map(p -> new Driver(p.getDriveId()))
                .collect(Collectors.toList());

        for(Driver driver : drives){
            if(!existDriverIdOnDatabase(driver.getDriverId())){
                driverRepository.save(driver);
            }
        }
        return drives;
    }
}
