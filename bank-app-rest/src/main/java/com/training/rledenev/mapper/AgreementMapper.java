package com.training.rledenev.mapper;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.entity.Agreement;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgreementMapper extends MapperDefault {

    @Mapping(source = "sum", target = "sum", qualifiedByName = "doubleToBigDecimal",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Agreement mapToEntity(AgreementDto agreementDto);

    @Named("toAgreementDto")
    @Mapping(source = "sum", target = "sum")
    @Mapping(source = "product.interestRate", target = "interestRate")
    @Mapping(source = "product.type", target = "productType")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.periodMonths", target = "periodMonths")
    @Mapping(source = "account.currencyCode", target = "currencyCode")
    AgreementDto mapToDto(Agreement agreement);

    @IterableMapping(qualifiedByName = "toAgreementDto")
    List<AgreementDto> mapToListDtos(List<Agreement> agreements);
}
