package com.training.rledenev.mapper;

import org.mapstruct.Named;

import java.math.BigDecimal;

public interface MapperDefault {

    @Named("stringToEnumName")
    default String stringToEnumName(String value) {
        return value.toUpperCase().replaceAll("\\s", "_");
    }

    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(Double value) {
        return BigDecimal.valueOf(value);
    }
}
