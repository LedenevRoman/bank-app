package com.training.rledenev.bankapp.mapper;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface AgreementMapper {

    @Mapping(source = "sum", target = "sum", qualifiedByName = "doubleToBigDecimal",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Agreement mapToEntity(AgreementDto agreementDto);
}
