package com.gas.fuelapp.service;

import com.gas.fuelapp.entity.FuelConsumption;
import com.gas.fuelapp.repository.FuelConsumptionRepository;
import com.gas.fuelapp.vo.*;
import org.apache.commons.collections.CollectionUtils;
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

    public Collection<FuelConsumption> saveFuelConsumptions(final SaveFuelConsumptionPayloadVO payload) {
        List<FuelConsumption> fuelConsumptions = payload.getFuelConsumptions().stream()
                .map(p -> new FuelConsumption(p.getFuelType().toUpperCase(), p.getLitters(), p.getTransactionDate(), p.getDriveId().toUpperCase(), p.getPricePerLitter()))
                .collect(Collectors.toList());
        return IteratorUtils.toList(fuelConsumptionRepository.saveAll(new ArrayList(fuelConsumptions)).iterator());
    }

    public TotalSpentAmountValueGroupedByMonthReportVO monthlyReportByYear(final BasicPayloadVO payload) {
        Collection<MonthlyReportVO> months = fuelConsumptionRepository.getMonthlyReportByYear((Objects.isNull(payload.getYear())) ? Calendar.getInstance().get(Calendar.YEAR) : payload.getYear());
        BigDecimal totalSpend = months.stream().map(MonthlyReportVO::getSpendValue).reduce(BigDecimal::add).get();
        return new TotalSpentAmountValueGroupedByMonthReportVO(months, totalSpend, payload.getYear());
    }

    public SpecifiedFuelConsumeByMonthAndYearReportVO fuelConsumptionReportByMonthAndYear(final SpecifiedFuelConsumeByMonthAndYearPayloadVO payload) {
        Collection<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.getFuelConsumptionReportByMonthAndYear(payload.getYear(), payload.getMonth());
        if(fuelConsumptions.isEmpty()){
            return new SpecifiedFuelConsumeByMonthAndYearReportVO(new StringBuilder().append(payload.getMonth()).append("/").append(payload.getYear()).toString());
        }
        BigDecimal totalValueSpend = fuelConsumptions.stream().map(FuelConsumption::getTotalValueSpend).reduce(BigDecimal::add).get();
        BigDecimal totalVolumeSpend = fuelConsumptions.stream().map(FuelConsumption::getLitters).reduce(BigDecimal::add).get();
        return new SpecifiedFuelConsumeByMonthAndYearReportVO(fuelConsumptions, new StringBuilder().append(payload.getMonth()).append("/").append(payload.getYear()).toString(), totalVolumeSpend, totalValueSpend);
    }

    public YearStatisticReportVO fuelMonthlyStatisticsConsumptionReportByMonthAndYear(final BasicPayloadVO payload) {

        return buildMonthlyStatisticReport(fuelConsumptionRepository.getMonthlyFuelConsumptionReportStatisticsByYear(payload.getYear()), payload.getYear());
    }

    private YearStatisticReportVO buildMonthlyStatisticReport(final Collection<FuelStatisticReportVO> fuelStatisticReportVOs, Integer year) {

        Set<Integer> months = fuelStatisticReportVOs.stream().map(FuelStatisticReportVO::getMonth).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(months)) {
            return null;
        } else {
            Collection<MonthlyStatisticReportVO> monthlyStatisticReportVOS = initializeMonthsCollection(months);

            for (FuelStatisticReportVO fuelStatisticReportVO : fuelStatisticReportVOs) {
                for (MonthlyStatisticReportVO monthlyStatisticRep : monthlyStatisticReportVOS) {
                    if (monthlyStatisticRep.getMonth().equals(fuelStatisticReportVO.getMonth())) {
                        monthlyStatisticRep.getRegistries().add(fuelStatisticReportVO);
                    }
                }
            }
            return new YearStatisticReportVO(monthlyStatisticReportVOS, year);
        }

    }


    private Collection<MonthlyStatisticReportVO> initializeMonthsCollection(final Set<Integer> months) {
        Collection<MonthlyStatisticReportVO> monthlyStatisticReportVOS = new ArrayList<>();

        for (Integer month : months) {
            monthlyStatisticReportVOS.add(new MonthlyStatisticReportVO(month));
        }

        return monthlyStatisticReportVOS;
    }

}