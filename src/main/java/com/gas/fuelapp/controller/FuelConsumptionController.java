package com.gas.fuelapp.controller;

import com.gas.fuelapp.exception.InvalidAccessException;
import com.gas.fuelapp.service.DriverService;
import com.gas.fuelapp.service.FuelService;
import com.gas.fuelapp.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/fuelConsumption")
public class FuelConsumptionController {

    @Autowired
    FuelService fuelService;

    @Autowired
    DriverService driverService;

    @RequestMapping(value = "/saveFuelConsumptions", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Object> saveFuelConsumptions(@RequestBody SaveFuelConsumptionPayloadVO payload) {
        validateDriverAccess(payload.getDriverId());
        fuelService.saveFuelConsumptions(payload);
        driverService.saveDriver(payload);
        return ResponseEntity.ok().body("Save Fuel Consumption(s) with success");
    }

    @RequestMapping(value = "/consumeByMonthAndYear", method = RequestMethod.POST)
    public SpecifiedFuelConsumeByMonthAndYearReportVO monthlyReportByYear(@RequestBody SpecifiedFuelConsumeByMonthAndYearPayloadVO payload) {
        validateDriverAccess(payload.getDriverId());
        return fuelService.fuelConsumptionReportByMonthAndYear(payload);
    }

    @RequestMapping(value = "/consumeGroupedByMonthAndYear", method = RequestMethod.POST)
    public TotalSpentAmountValueGroupedByMonthReportVO monthlyReport(@RequestBody BasicPayloadVO payload) {
        validateDriverAccess(payload.getDriverId());
        return fuelService.monthlyReportByYear(payload);
    }

    @RequestMapping(value = "/monthlyStatisticsByYear", method = RequestMethod.POST)
    public YearStatisticReportVO fuelMonthlyStatisticsConsumptionReportByMonthAndYear(@RequestBody BasicPayloadVO payload) {
        validateDriverAccess(payload.getDriverId());
        return fuelService.fuelMonthlyStatisticsConsumptionReportByMonthAndYear(payload);
    }

    private void validateDriverAccess(String driverId) {
        if (StringUtils.isBlank(driverId) || !driverService.existDriverIdOnDatabase(driverId)) {
            log.error("Invalid access for Driver id: {}", driverId);
            throw new InvalidAccessException(String.format("This Driver Id [%s] does not have access", driverId));
        }
    }
}
