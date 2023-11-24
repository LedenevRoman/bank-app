package com.training.rledenev.service;

import com.training.rledenev.dto.AgreementDto;

import java.util.List;

public interface AgreementService {
    AgreementDto createNewAgreement(AgreementDto agreementDto);

    List<AgreementDto> getAgreementsForManager();

    void confirmAgreementByManager(Long agreementId);

    void blockAgreementByManager(Long agreementId);

    AgreementDto getAgreementDtoById(Long id);
}
