package com.gas.fuelapp.repository;

import com.gas.fuelapp.entity.FuelConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final FuelConsumptionRepository fuelConsumptionRepository;

    @Autowired
    public DatabaseLoader(FuelConsumptionRepository fuelConsumptionRepo) {
        this.fuelConsumptionRepository = fuelConsumptionRepo;
    }

    @Override
    public void run(String... strings) {

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina".toUpperCase(), new BigDecimal(54.5), new Date(), "firstDriveId", new BigDecimal(1.30)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina".toUpperCase(), new BigDecimal(50), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), "SecondDriveId", new BigDecimal(2.50)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina".toUpperCase(), new BigDecimal(100), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), "SecondDriveId", new BigDecimal(5.05)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina".toUpperCase(), new BigDecimal(32.5), new GregorianCalendar(2014, Calendar.MAY, 9).getTime(), "firstDriveId", new BigDecimal(2.35)));

        this.fuelConsumptionRepository.save(new FuelConsumption("gasolina".toUpperCase(), new BigDecimal(54.50), new GregorianCalendar(2014, Calendar.MAY, 11).getTime(), "thirdDriveId", new BigDecimal(2.00)));

        this.fuelConsumptionRepository.save(new FuelConsumption("diesel".toUpperCase(), new BigDecimal(25.15), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), "SecondDriveId", new BigDecimal(5.09)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Diesel".toUpperCase(), new BigDecimal(19.05), new GregorianCalendar(2014, Calendar.MAY, 9).getTime(), "firstDriveId", new BigDecimal(2.45)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Alcohol".toUpperCase(), new BigDecimal(14.05), new GregorianCalendar(2014, Calendar.JUNE, 11).getTime(), "thirdDriveId", new BigDecimal(2.00)));
    }

}
