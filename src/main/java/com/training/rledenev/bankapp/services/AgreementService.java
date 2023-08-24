package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Agreement;

public interface AgreementService {
    Agreement createAccount(AgreementDto agreementDto);
}
