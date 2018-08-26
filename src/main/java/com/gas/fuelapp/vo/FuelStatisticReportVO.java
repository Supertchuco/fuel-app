package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FuelStatisticReportVO implements Serializable {

    @JsonProperty("Fuel Type")
    private String fuelType;

    @JsonProperty("Volume")
    private BigDecimal litters;

    @JsonProperty("Average Price")
    private BigDecimal averagePrice;

    @JsonIgnore
    private Integer month;

    @JsonIgnore
    private Integer year;

    public FuelStatisticReportVO(String fuelType, BigDecimal litters, double averagePrice, int month, int year){
        this.fuelType = fuelType;
        this.litters = litters;
        this.averagePrice = new BigDecimal(averagePrice).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.month = month;
        this.year = year;
    }

}
