package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpecifiedFuelConsumeByMonthAndYearPayloadVO implements Serializable {

    @JsonProperty("Month")
    private int month;

    @JsonProperty("Year")
    private int year;

    @JsonProperty("Drive Id")
    private String driverId;

    SpecifiedFuelConsumeByMonthAndYearPayloadVO(int month, int year, String driverId) {
        this.month = month;
        this.year = year;
        this.driverId = driverId;
    }
}