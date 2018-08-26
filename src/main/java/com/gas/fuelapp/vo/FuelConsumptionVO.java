package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class FuelConsumptionVO implements Serializable {

    @JsonProperty("Fuel Type")
    private String fuelType;

    @JsonProperty("Drive Id")
    private String driveId;

    @JsonProperty("Volume")
    private BigDecimal litters;

    @JsonProperty("Price Per Litter")
    private BigDecimal pricePerLitter;

    @JsonProperty("Date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyyy")
    private Date transactionDate;

    public FuelConsumptionVO(String fuelType, String driveId, BigDecimal litters, BigDecimal pricePerLitter, Date transactionDate) {
        this.fuelType = fuelType;
        this.driveId = driveId;
        this.litters = litters;
        this.pricePerLitter = pricePerLitter;
        this.transactionDate = transactionDate;
    }

}
