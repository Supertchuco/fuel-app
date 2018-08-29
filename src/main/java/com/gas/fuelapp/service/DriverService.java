package com.gas.fuelapp.service;

import com.gas.fuelapp.entity.Driver;
import com.gas.fuelapp.exception.SaveFuelConsumptionException;
import com.gas.fuelapp.repository.DriverRepository;
import com.gas.fuelapp.vo.SaveFuelConsumptionPayloadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    public boolean existDriverIdOnDatabase(final String driverId) {
        return (driverRepository.findByDriverId(driverId).isEmpty()) ? false : true;
    }

    public Collection<Driver> saveDriver(final SaveFuelConsumptionPayloadVO payload) {

        try {
            List<Driver> drives = payload.getFuelConsumptions().stream()
                    .map(p -> new Driver(p.getDriveId()))
                    .collect(Collectors.toList());

            for (Driver driver : drives) {
                if (!existDriverIdOnDatabase(driver.getDriverId())) {
                    driverRepository.save(driver);
                }
            }
            log.info("Drive(s) save with success");
            return drives;
        } catch (Exception e) {
            log.error("Error to save drive(s)", e);
            throw new SaveFuelConsumptionException("Error to save new Driver");
        }

    }
}
