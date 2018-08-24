package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyReportVO implements Serializable {

    @JsonProperty("Month")
    private int month;

    @JsonProperty("Year")
    private int year;

    @JsonProperty("Volume")
    private double litters;

    @JsonProperty("Spend Value")
    private BigDecimal spendValue;

}
