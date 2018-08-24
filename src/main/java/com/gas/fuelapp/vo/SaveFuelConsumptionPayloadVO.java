package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gas.fuelapp.entity.FuelConsumption;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@Data
public class SaveFuelConsumptionPayloadVO implements Serializable {

    @JsonProperty("Fuel Consumptions")
    @NotNull
    private Collection<FuelConsumptionVO> fuelConsumptions;

    @JsonProperty("Driver Id")
    @NotNull
    private String driverId;
}
