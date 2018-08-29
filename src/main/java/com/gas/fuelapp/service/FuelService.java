package com.gas.fuelapp.service;

import com.gas.fuelapp.entity.FuelConsumption;
import com.gas.fuelapp.exception.BadRequestSaveFuelConsumptionException;
import com.gas.fuelapp.exception.DuplicateFuelConsumptionException;
import com.gas.fuelapp.exception.GenerateReportException;
import com.gas.fuelapp.exception.SaveFuelConsumptionException;
import com.gas.fuelapp.repository.FuelConsumptionRepository;
import com.gas.fuelapp.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.upperCase;

@Service
@Slf4j
public class FuelService {

    @Autowired
    FuelConsumptionRepository fuelConsumptionRepository;

    public Collection<FuelConsumption> saveFuelConsumptions(final SaveFuelConsumptionPayloadVO payload) {

        List<FuelConsumption> fuelConsumptions = payload.getFuelConsumptions().stream()
                .map(p -> new FuelConsumption(upperCase(p.getFuelType()), p.getLitters(), p.getTransactionDate(), upperCase(p.getDriveId()), p.getPricePerLitter()))
                .collect(Collectors.toList());
        isValidFuelConsumptionObjects(fuelConsumptions);
        try {
            Collection<FuelConsumption> fuelConsumpt = IteratorUtils.toList(fuelConsumptionRepository.saveAll(new ArrayList(fuelConsumptions)).iterator());
            log.info("Fuel Consumption(s) save with success");
            return fuelConsumpt;

        } catch (Exception e) {
            log.error("Error to save drive(s)", e);
            throw new SaveFuelConsumptionException("Error to save new Fuel Consumption(s)");
        }

    }

    public TotalSpentAmountValueGroupedByMonthReportVO monthlyReportByYear(final BasicPayloadVO payload) {
        try {
            Integer year = (Objects.isNull(payload.getYear())) ? Calendar.getInstance().get(Calendar.YEAR) : payload.getYear();
            Collection<MonthlyReportVO> months = fuelConsumptionRepository.getMonthlyReportByYear(year);
            if (months.isEmpty()) {
                return new TotalSpentAmountValueGroupedByMonthReportVO(null, null, year);
            }
            BigDecimal totalSpend = months.stream().map(MonthlyReportVO::getSpendValue).reduce(BigDecimal::add).get();
            log.info("Monthly By Year Report Generate with Success");
            return new TotalSpentAmountValueGroupedByMonthReportVO(months, totalSpend, payload.getYear());
        } catch (Exception e) {
            log.error("Error to Generate Monthly By Year Report", e);
            throw new GenerateReportException("Error to Generate Monthly By Year Report");
        }
    }

    public SpecifiedFuelConsumeByMonthAndYearReportVO fuelConsumptionReportByMonthAndYear(final SpecifiedFuelConsumeByMonthAndYearPayloadVO payload) {
        try {
            Integer year = (Objects.isNull(payload.getYear())) ? Calendar.getInstance().get(Calendar.YEAR) : payload.getYear();
            basicReportValidation(payload.getMonth());
            Collection<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.getFuelConsumptionReportByMonthAndYear(year, payload.getMonth());
            if (fuelConsumptions.isEmpty()) {
                return new SpecifiedFuelConsumeByMonthAndYearReportVO(new StringBuilder().append(payload.getMonth()).append("/").append(payload.getYear()).toString());
            }
            BigDecimal totalValueSpend = fuelConsumptions.stream().map(FuelConsumption::getTotalValueSpend).reduce(BigDecimal::add).get();
            BigDecimal totalVolumeSpend = fuelConsumptions.stream().map(FuelConsumption::getLitters).reduce(BigDecimal::add).get();
            log.info("Fuel Consumption Report By Month And Year Report Generate with Success");
            return new SpecifiedFuelConsumeByMonthAndYearReportVO(fuelConsumptions, new StringBuilder().append(payload.getMonth()).append("/").append(payload.getYear()).toString(), totalVolumeSpend, totalValueSpend);
        } catch (GenerateReportException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error to Generate Fuel Consumption Report By Month And Year Report", e);
            throw new GenerateReportException("Error to Generate Fuel Consumption Report By Month And Year Report");
        }
    }

    public YearStatisticReportVO fuelMonthlyStatisticsConsumptionReportByMonthAndYear(final BasicPayloadVO payload) {
        try {
            Integer year = (Objects.isNull(payload.getYear())) ? Calendar.getInstance().get(Calendar.YEAR) : payload.getYear();
            YearStatisticReportVO yearStatisticReportVO = buildMonthlyStatisticReport(fuelConsumptionRepository.getMonthlyFuelConsumptionReportStatisticsByYear(payload.getYear()), year);
            log.info("Fuel Monthly Statistics Consumption Report By Month And Year Report Generate with Success");
            return yearStatisticReportVO;
        } catch (Exception e) {
            log.error("Error to Generate Fuel Monthly Statistics Consumption Report By Month And Year Report", e);
            throw new GenerateReportException("Error to Generate Fuel Monthly Statistics Consumption Report By Month And Year Report");
        }
    }

    private YearStatisticReportVO buildMonthlyStatisticReport(final Collection<FuelStatisticReportVO> fuelStatisticReportVOs, Integer year) {
        Set<Integer> months = fuelStatisticReportVOs.stream().map(FuelStatisticReportVO::getMonth).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(months)) {
            return new YearStatisticReportVO(null, year);
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

    private void isValidFuelConsumptionObjects(List<FuelConsumption> fuelConsumptions) {
        if (CollectionUtils.isEmpty(fuelConsumptions)) {
            log.error("Error on request, fuel consumption on  array on request is empty");
            throw new BadRequestSaveFuelConsumptionException("Invalid request to save new Fuel Consumption(s), fuel consumption(s) on array on request is empty");
        }

        try {
            for (FuelConsumption fuelConsumption : fuelConsumptions) {
                if (fuelConsumption.existNullField()) {
                    log.error("Error on request");
                    throw new BadRequestSaveFuelConsumptionException("Invalid request to save new Fuel Consumption(s)");
                }

                Collection<FuelConsumption> fuelConsumptionsDuplicated = fuelConsumptionRepository.findDuplicates(fuelConsumption.getFuelType(), fuelConsumption.getLitters(), fuelConsumption.getTransactionDate(), fuelConsumption.getPricePerLitter(), fuelConsumption.getDriveId());
                if (CollectionUtils.isNotEmpty(fuelConsumptionsDuplicated)) {
                    throw new DuplicateFuelConsumptionException("Duplicated Fuel Consumption on request");
                }

            }
        } catch (IllegalAccessException e) {
            log.error("Error validate Fuel Consumption Objects", e);
            throw new SaveFuelConsumptionException("Error to save new Fuel Consumption(s)");
        }
    }

    private void basicReportValidation(Integer month) {
        if (month == null) {
            log.error("Error to Generate Report, invalid payload");
            throw new GenerateReportException("Error to Generate Report, invalid payload");
        }
    }

}