package com.gas.fuelapp.utils;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Converter(autoApply = true)
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(MonetaryAmount attribute) {
        BigDecimal big = null;
        if (attribute != null) {
            big = new BigDecimal(attribute.getNumber().toString()).setScale(5, RoundingMode.HALF_EVEN);
        }
        return big;
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(BigDecimal dbData) {
        MonetaryAmount asAmount = null;
        if (dbData != null) {
            asAmount = Money.of(dbData.doubleValue(), "EUR");
        }
        return asAmount;
    }
}