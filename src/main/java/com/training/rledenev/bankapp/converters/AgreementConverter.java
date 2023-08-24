package com.training.rledenev.bankapp.converters;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.enums.Status;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class AgreementConverter {
    public Agreement fromDtoToEntity(AgreementDto agreementDto) {
        Agreement agreement = new Agreement();
        return agreement.setInterestRate(BigDecimal.valueOf(agreementDto.getInterestRate()))
                .setStatus(Status.values()[agreementDto.getStatus()])
                .setSum(BigDecimal.valueOf(agreementDto.getSum()))
                .setCreatedAt(LocalDateTime.now());
    }
}
