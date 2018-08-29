package com.gas.fuelapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalSpentAmountValueGroupedByMonthReportVO implements Serializable {

    @JsonProperty("Months")
    private Collection<MonthlyReportVO> months;

    @JsonProperty("Total Value Spend")
    private BigDecimal totalValueSpend;

    @JsonProperty("Year selected")
    private Integer year;
}
