package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpecifiedFuelConsumeByMonthAndYearPayloadVO implements Serializable {

    @JsonProperty("Month")
    private Integer month;

    @JsonProperty("Year")
    private Integer year;

    @JsonProperty("Driver Id")
    private String driverId;

    SpecifiedFuelConsumeByMonthAndYearPayloadVO(Integer month, Integer year, String driverId) {
        this.month = month;
        this.year = year;
        this.driverId = driverId;
    }
}