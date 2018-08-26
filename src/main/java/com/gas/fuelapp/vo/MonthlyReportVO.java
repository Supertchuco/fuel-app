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
    private Integer month;

    @JsonProperty("Year")
    private Integer year;

    @JsonProperty("Volume")
    private BigDecimal litters;

    @JsonProperty("Spend Value")
    private BigDecimal spendValue;

}
