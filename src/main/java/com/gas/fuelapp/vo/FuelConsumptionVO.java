package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelConsumptionVO implements Serializable {

    @NotNull
    @JsonProperty("Fuel Type")
    private String fuelType;

    @NotNull
    @JsonProperty("Drive Id")
    private String driveId;

    @NotNull
    @JsonProperty("Volume")
    private BigDecimal litters;

    @NotNull
    @JsonProperty("Price Per Litter")
    private BigDecimal pricePerLitter;

    @NotNull
    @JsonProperty("Date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyyy")
    private Date transactionDate;

}
