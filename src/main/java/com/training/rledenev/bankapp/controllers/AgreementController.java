package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.services.AgreementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/agreement")
public class AgreementController {
    private final AgreementService agreementService;

    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createManager(@RequestBody AgreementDto agreementDto) {
        Agreement agreement = agreementService.createNewAgreement(agreementDto);
        return ResponseEntity.created(URI.create("/" + agreement.getId())).body(agreement.getId());
    }
}
