package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasicPayloadVO {

    @JsonProperty("Year")
    private Integer year;

    @JsonProperty("Driver Id")
    private String driverId;

}
