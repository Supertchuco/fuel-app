package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gas.fuelapp.entity.FuelConsumption;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@AllArgsConstructor
public class SpecifiedFuelConsumeByMonthAndYearReportVO implements Serializable {

    @JsonProperty("Consume")
    private Collection<FuelConsumption> fuelConsumption;

    @JsonProperty("Month and Year")
    private String monthYear;

    @JsonProperty("Total Volume")
    private double litters;

    @JsonProperty("Total Price")
    private BigDecimal spendValue;

}