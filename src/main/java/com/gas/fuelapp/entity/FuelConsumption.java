package com.gas.fuelapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "FuelConsumption")
@Table(name = "FuelConsumption")
public class FuelConsumption implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column
    private int transactionId;

    @Column
    @JsonProperty("Fuel Type")
    private String fuelType;

    @Column
    @JsonProperty("Volume")
    private double litters;

    @Column
    @JsonProperty("Date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyyy")
    private Date transactionDate;

    @Column
    @JsonProperty("Price Per Litter")
    private BigDecimal pricePerLitter;

    @Column
    @JsonProperty("Drive Id")
    private String driveId;

    @Column
    @JsonIgnore
    @JsonProperty("Total Value Spend")
    private BigDecimal totalValueSpend;

    public FuelConsumption(String fuelType, double litters, Date transactionDate, String driveId, BigDecimal pricePerLitter) {
        this.fuelType = fuelType;
        this.litters = litters;
        this.transactionDate = transactionDate;
        this.driveId = driveId;
        this.pricePerLitter = pricePerLitter;
        this.totalValueSpend = calculateTotalValueSpend();
    }

    private BigDecimal calculateTotalValueSpend() {
        return pricePerLitter.multiply(new BigDecimal(litters));
    }

}
