package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.AgreementDto;

import java.util.List;

public interface AgreementService {
    AgreementDto createNewAgreement(AgreementDto agreementDto);

    List<AgreementDto> getAgreementsForManager();

    void confirmAgreementByManager(Long agreementId);

    void blockAgreementByManager(Long agreementId);

    AgreementDto getAgreementDtoById(Long id);
}
