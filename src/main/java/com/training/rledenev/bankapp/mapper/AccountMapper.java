package com.training.rledenev.bankapp.mapper;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "accountType", target = "accountType", qualifiedByName = "stringToUpperCase")
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToUpperCase")
    @Mapping(source = "currencyCode", target = "currencyCode", qualifiedByName = "stringToUpperCase")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "doubleToBigDecimal")
    Account mapToEntity(AccountDto accountDto);

    @Named("stringToUpperCase")
    default String stringToUpperCase(String value) {
        return value != null ? value.toUpperCase() : null;
    }
    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(Double balance) {
        return balance != null ? BigDecimal.valueOf(balance) : null;
    }
}
