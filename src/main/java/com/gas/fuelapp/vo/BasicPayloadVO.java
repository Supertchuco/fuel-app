package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasicPayloadVO {

    @JsonProperty("Year")
    private int year;

    @JsonProperty("Drive Id")
    private String driverId;

}
