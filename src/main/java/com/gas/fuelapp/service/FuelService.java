package com.gas.fuelapp.service;

import com.gas.fuelapp.entity.FuelConsumption;
import com.gas.fuelapp.repository.FuelConsumptionRepository;
import com.gas.fuelapp.vo.*;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuelService {

    @Autowired
    FuelConsumptionRepository fuelConsumptionRepository;

    public Collection<FuelConsumption> findAll() {
        return fuelConsumptionRepository.findAll();
    }

    public Collection<Date> findAllExistentYearsOnDb() {
        return fuelConsumptionRepository.findAllYearsOnDatabase();
    }

    public Collection<MonthlyReportVO> monthlyReportAllYears() {
        return fuelConsumptionRepository.getMonthlyReportAllYears();
    }

    public Collection<FuelConsumption> saveFuelConsumptions(SaveFuelConsumptionPayloadVO payload) {
        List<FuelConsumption> fuelConsumptions = payload.getFuelConsumptions().stream()
                .map(p -> new FuelConsumption(p.getFuelType(), p.getLitters(), p.getTransactionDate(), p.getDriveId(), p.getPricePerLitter()))
                .collect(Collectors.toList());
        return IteratorUtils.toList(fuelConsumptionRepository.saveAll(new ArrayList(fuelConsumptions)).iterator());
    }

    public TotalSpentAmountValueGroupedByMonthReportVO monthlyReportByYear(BasicPayloadVO payload) {
        Collection<MonthlyReportVO> months = fuelConsumptionRepository.getMonthlyReportByYear((Objects.isNull(payload.getYear()))? Calendar.getInstance().get(Calendar.YEAR) : payload.getYear());
        BigDecimal totalSpend = months.stream().map(MonthlyReportVO::getSpendValue).reduce(BigDecimal::add).get();
        return new TotalSpentAmountValueGroupedByMonthReportVO(months, totalSpend, payload.getYear());
    }

    public SpecifiedFuelConsumeByMonthAndYearReportVO fuelConsumptionReportByMonthAndYear(SpecifiedFuelConsumeByMonthAndYearPayloadVO payload) {
        Collection<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.getFuelConsumptionReportByMonthAndYear(payload.getMonth(), payload.getYear());
        BigDecimal totalValueSpend = fuelConsumptions.stream().map(FuelConsumption::getTotalValueSpend).reduce(BigDecimal::add).get();
        Double totalVolumeSpend = fuelConsumptions.stream().map(FuelConsumption::getLitters).reduce(Double::sum).get();
        return new SpecifiedFuelConsumeByMonthAndYearReportVO(fuelConsumptions, new StringBuilder().append(payload.getMonth()).append("/").append(payload.getYear()).toString(), totalVolumeSpend, totalValueSpend);
    }



}