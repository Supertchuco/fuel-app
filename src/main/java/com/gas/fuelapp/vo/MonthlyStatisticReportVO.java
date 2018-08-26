package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
public class MonthlyStatisticReportVO implements Serializable {

    @JsonProperty("Month")
    private Integer month;

    @JsonProperty("Registries")
    private Collection<FuelStatisticReportVO> registries;

    public MonthlyStatisticReportVO(Integer month){
        this.month = month;
        this.registries = new ArrayList<>();
    }

}
