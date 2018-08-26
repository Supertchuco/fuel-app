package com.gas.fuelapp.controller;

import com.gas.fuelapp.entity.Driver;
import com.gas.fuelapp.entity.FuelConsumption;
import com.gas.fuelapp.service.DriverService;
import com.gas.fuelapp.service.FuelService;
import com.gas.fuelapp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/fuelConsumption")
public class FuelConsumptionController {

    @Autowired
    FuelService fuelService;

    @Autowired
    DriverService driverService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public @ResponseBody
    Collection<FuelConsumption> findAll() {
        return fuelService.findAll();
    }

    @RequestMapping(value = "/findAllYears", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Date> findAllYears() {
        return fuelService.findAllExistentYearsOnDb();
    }

    @RequestMapping(value = "/saveFuelConsumptions", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> saveFuelConsumptions(@RequestBody SaveFuelConsumptionPayloadVO payload) {
        fuelService.saveFuelConsumptions(payload);
        driverService.saveDriver(payload);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/consumeByMonthAndYear", method = RequestMethod.POST)
    public SpecifiedFuelConsumeByMonthAndYearReportVO monthlyReportByYear(@RequestBody SpecifiedFuelConsumeByMonthAndYearPayloadVO payload) {
        return fuelService.fuelConsumptionReportByMonthAndYear(payload);
    }

    @RequestMapping(value = "/consumeGroupedByMonth", method = RequestMethod.POST)
    public TotalSpentAmountValueGroupedByMonthReportVO monthlyReport(@RequestBody BasicPayloadVO payload) {
        return fuelService.monthlyReportByYear(payload);
    }

    @RequestMapping(value = "/monthlyStatistics", method = RequestMethod.POST)
    public YearStatisticReportVO fuelMonthlyStatisticsConsumptionReportByMonthAndYear(@RequestBody BasicPayloadVO payload) {
        return fuelService.fuelMonthlyStatisticsConsumptionReportByMonthAndYear(payload);
    }


}
