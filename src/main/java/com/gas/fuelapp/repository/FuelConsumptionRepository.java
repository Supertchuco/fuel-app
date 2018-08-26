package com.gas.fuelapp.repository;

import com.gas.fuelapp.entity.FuelConsumption;
import com.gas.fuelapp.vo.FuelStatisticReportVO;
import com.gas.fuelapp.vo.MonthlyReportVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;

public interface FuelConsumptionRepository extends CrudRepository<FuelConsumption, Long> {

    Collection<FuelConsumption> findByFuelType(String fuelName);

    Collection<FuelConsumption> findAll();

    @Query("SELECT DISTINCT YEAR(transactionDate) FROM FuelConsumption")
    Collection<Date> findAllYearsOnDatabase();

    @Query("SELECT new com.gas.fuelapp.vo.MonthlyReportVO(MONTH(fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate), SUM(fuelConsumption.litters), SUM(fuelConsumption.totalValueSpend)) FROM FuelConsumption fuelConsumption GROUP BY MONTH(fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate)")
    Collection<MonthlyReportVO> getMonthlyReportAllYears();

    @Query("SELECT new com.gas.fuelapp.vo.MonthlyReportVO(MONTH(fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate), SUM(fuelConsumption.litters), SUM(fuelConsumption.totalValueSpend)) FROM FuelConsumption fuelConsumption WHERE YEAR(fuelConsumption.transactionDate)=:yearParam GROUP BY MONTH(fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate)")
    Collection<MonthlyReportVO> getMonthlyReportByYear(@Param("yearParam") Integer yearParam);

    @Query("SELECT new com.gas.fuelapp.vo.MonthlyReportVO(MONTH(fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate), SUM(fuelConsumption.litters), SUM(fuelConsumption.totalValueSpend)) FROM FuelConsumption fuelConsumption WHERE fuelConsumption.transactionDate BETWEEN :startDate AND :endDate GROUP BY MONTH(fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate)")
    Collection<MonthlyReportVO> getMonthlyReportTimeInterval(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT fuelConsumption FROM FuelConsumption fuelConsumption WHERE YEAR(fuelConsumption.transactionDate)=:yearParam AND MONTH(fuelConsumption.transactionDate)=:monthParam")
    Collection<FuelConsumption> getFuelConsumptionReportByMonthAndYear(@Param("yearParam") int yearParam, @Param("monthParam") int monthParam);

    @Query("SELECT new com.gas.fuelapp.vo.FuelStatisticReportVO(fuelConsumption.fuelType, SUM(fuelConsumption.litters), AVG(fuelConsumption.pricePerLitter), MONTH( fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate)) FROM FuelConsumption fuelConsumption WHERE YEAR(fuelConsumption.transactionDate)=:yearParam GROUP BY MONTH(fuelConsumption.transactionDate), YEAR(fuelConsumption.transactionDate), fuelConsumption.fuelType ORDER BY MONTH(fuelConsumption.transactionDate)")
    Collection<FuelStatisticReportVO> getMonthlyFuelConsumptionReportStatisticsByYear(@Param("yearParam") int yearParam);

}