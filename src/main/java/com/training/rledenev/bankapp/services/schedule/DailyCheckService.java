package com.training.rledenev.bankapp.services.schedule;

import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import com.training.rledenev.bankapp.repository.AgreementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyCheckService {
    private final AgreementRepository agreementRepository;

    public DailyCheckService(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    public void dailyCheckAgreements() {
        Map<ProductType, List<Agreement>> typeAgreementsMap = agreementRepository.findAll().stream()
                .collect(Collectors.groupingBy(agreement -> agreement.getProduct().getType(),
                        Collectors.mapping(agreement -> agreement, Collectors.toList())));
    }
}
