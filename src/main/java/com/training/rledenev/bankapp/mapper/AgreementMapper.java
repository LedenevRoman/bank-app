package com.training.rledenev.bankapp.mapper;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, ProductMapper.class})
public interface AgreementMapper {

    @Mapping(source = "sum", target = "sum", qualifiedByName = "doubleToBigDecimal",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Agreement mapToEntity(AgreementDto agreementDto);

    @Named("toAgreementDto")
    @Mapping(source = "sum", target = "sum", qualifiedByName = "bigDecimalToDouble",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(source = "product", target = "interestRate", qualifiedByName = "getInterestRateFromProduct",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(source = "product", target = "productName", qualifiedByName = "getProductName")
    @Mapping(source = "account", target = "currencyCode", qualifiedByName = "getCurrencyCodeFromAccount")
    AgreementDto mapToDto(Agreement agreement);

    @IterableMapping(qualifiedByName = "toAgreementDto")
    List<AgreementDto> mapToListDtos(List<Agreement> agreements);

    @Named("getProductName")
    default String getProductName(Product product) {
        return product.getName();
    }

    @Named("getCurrencyCodeFromAccount")
    default String getCurrencyCodeFromAccount(Account account) {
        return account.getCurrencyCode().toString();
    }

    @Named("getInterestRateFromProduct")
    default Double getInterestRateFromProduct(Product product) {
        return product.getInterestRate().doubleValue();
    }
}
