package com.gas.fuelapp.repository;

import com.gas.fuelapp.entity.FuelConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.money.Monetary;
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

/*        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina", 54.5, new Date(), "firstDriveId", Monetary.getDefaultAmountFactory()
                .setCurrency("EUR").setNumber(1.30).create()));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina", 100, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), "SecondDriveId", Monetary.getDefaultAmountFactory()
                .setCurrency("EUR").setNumber(5.00).create()));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina", 32.5, new GregorianCalendar(2014, Calendar.MAY, 9).getTime(), "firstDriveId", Monetary.getDefaultAmountFactory()
                .setCurrency("EUR").setNumber(2.30).create()));*/

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina", 54.5, new Date(), "firstDriveId",new BigDecimal(1.30)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina", 100, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), "SecondDriveId", new BigDecimal(5.00)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina", 32.5, new GregorianCalendar(2014, Calendar.MAY, 9).getTime(), "firstDriveId", new BigDecimal(2.30)));

        this.fuelConsumptionRepository.save(new FuelConsumption("Gasolina", 32.5, new GregorianCalendar(2014, Calendar.MAY, 11).getTime(), "thirdDriveId", new BigDecimal(2.00)));
    }

}
