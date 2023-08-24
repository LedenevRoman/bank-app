package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.converters.AgreementConverter;
import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.repository.AgreementRepository;
import com.training.rledenev.bankapp.services.AgreementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgreementServiceImpl implements AgreementService {
    private final AgreementConverter agreementConverter;
    private final AgreementRepository agreementRepository;

    public AgreementServiceImpl(AgreementConverter agreementConverter, AgreementRepository agreementRepository) {
        this.agreementConverter = agreementConverter;
        this.agreementRepository = agreementRepository;
    }

    @Transactional
    @Override
    public Agreement createAccount(AgreementDto agreementDto) {
        Agreement agreement = agreementConverter.fromDtoToEntity(agreementDto);
        agreementRepository.save(agreement);
        return agreement;
    }
}
