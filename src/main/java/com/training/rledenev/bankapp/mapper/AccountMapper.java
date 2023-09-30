package com.training.rledenev.bankapp.mapper;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToEnumName")
    @Mapping(source = "currencyCode", target = "currencyCode", qualifiedByName = "stringToEnumName")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "doubleToBigDecimal",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Account mapToEntity(AccountDto accountDto);

    @Named("toAccountDto")
    @Mapping(source = "agreement.product.name", target = "productName")
    @Mapping(source = "agreement.product.interestRate", target = "interestRate")
    @Mapping(source = "client", target = "owner", qualifiedByName = "getOwnerFullNameFromClient")
    @Mapping(source = "agreement", target = "paymentTerm", qualifiedByName = "getPaymentTermFromAgreement")
    @Mapping(source = "currencyCode", target = "currencyName", qualifiedByName = "getCurrencyNameFromCode")
    @Mapping(source = "agreement.product.type", target = "type")
    @Mapping(source = "agreement.startDate", target = "startDate")
    AccountDto mapToDto(Account account);

    @IterableMapping(qualifiedByName = "toAccountDto")
    List<AccountDto> mapToListDtos(List<Account> accountsOfUser);

    @Named("stringToEnumName")
    default String stringToEnumName(String value) {
        return value.toUpperCase().replaceAll("\\s", "_");
    }

    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(Double value) {
        return BigDecimal.valueOf(value);
    }

    @Named("getOwnerFullNameFromClient")
    default String getOwnerFullNameFromClient(User client) {
        return String.format("%s %s", client.getFirstName(), client.getLastName());
    }

    @Named("getPaymentTermFromAgreement")
    default LocalDate getPaymentTermFromAgreement(Agreement agreement) {
        LocalDate startDate = agreement.getStartDate();
        int periodMonth = agreement.getPeriodMonths();
        return startDate.plusMonths(periodMonth);
    }

    @Named("getCurrencyNameFromCode")
    default String getCurrencyNameFromCode(CurrencyCode currencyCode) {
        return currencyCode.getCurrencyName();
    }
}
