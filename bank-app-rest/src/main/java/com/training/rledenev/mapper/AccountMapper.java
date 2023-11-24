package com.training.rledenev.mapper;

import com.training.rledenev.dto.AccountDto;
import com.training.rledenev.entity.Account;
import com.training.rledenev.entity.Agreement;
import com.training.rledenev.entity.User;
import com.training.rledenev.entity.enums.CurrencyCode;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper extends MapperDefault {

    @Named("toAccountDto")
    @Mapping(source = "agreement.product.name", target = "productName")
    @Mapping(source = "agreement.product.interestRate", target = "interestRate")
    @Mapping(source = "client", target = "owner", qualifiedByName = "getUserFullName")
    @Mapping(source = "agreement.manager", target = "manager", qualifiedByName = "getUserFullName",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(source = "agreement", target = "paymentTerm", qualifiedByName = "getPaymentTermFromAgreement")
    @Mapping(source = "currencyCode", target = "currencyName", qualifiedByName = "getCurrencyNameFromCode")
    @Mapping(source = "agreement.product.type", target = "type")
    @Mapping(source = "agreement.startDate", target = "startDate")
    AccountDto mapToDto(Account account);

    @IterableMapping(qualifiedByName = "toAccountDto")
    List<AccountDto> mapToListDtos(List<Account> accountsOfUser);

    @Named("getUserFullName")
    default String getUserFullName(User user) {
        return String.format("%s %s", user.getFirstName(), user.getLastName());
    }

    @Named("getPaymentTermFromAgreement")
    default LocalDate getPaymentTermFromAgreement(Agreement agreement) {
        if (agreement.getStartDate() == null) {
            return null;
        }
        LocalDate startDate = agreement.getStartDate();
        int periodMonth = agreement.getProduct().getPeriodMonths();
        return startDate.plusMonths(periodMonth);
    }

    @Named("getCurrencyNameFromCode")
    default String getCurrencyNameFromCode(CurrencyCode currencyCode) {
        return currencyCode.getCurrencyName();
    }
}
