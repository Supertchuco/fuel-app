package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class YearStatisticReportVO {

    @JsonProperty("Months")
    private Collection<MonthlyStatisticReportVO> registries;

    @JsonProperty("Year")
    private Integer year;
}
