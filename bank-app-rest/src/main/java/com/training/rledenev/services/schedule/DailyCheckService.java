package com.training.rledenev.services.schedule;

import com.training.rledenev.entity.Agreement;
import com.training.rledenev.entity.enums.ProductType;
import com.training.rledenev.repository.AgreementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DailyCheckService {
    private final AgreementRepository agreementRepository;

    public void dailyCheckAgreements() {
        Map<ProductType, List<Agreement>> typeAgreementsMap = agreementRepository.findAll().stream()
                .collect(Collectors.groupingBy(agreement -> agreement.getProduct().getType(),
                        Collectors.mapping(agreement -> agreement, Collectors.toList())));
    }
}
