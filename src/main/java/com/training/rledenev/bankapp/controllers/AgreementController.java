package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.services.AgreementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/agreement")
public class AgreementController {
    private final AgreementService agreementService;

    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createNewAgreement(@RequestBody AgreementDto agreementDto) {
        agreementDto = agreementService.createNewAgreement(agreementDto);
        return ResponseEntity.created(URI.create("/" + agreementDto.getId())).body(agreementDto.getId());
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<AgreementDto>> getNewAgreements() {
        List<AgreementDto> agreementDtos = agreementService.getAgreementsForManager();
        return ResponseEntity.ok().body(agreementDtos);
    }

    @PutMapping("/confirm/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> confirmAgreement(@PathVariable(name = "id") Long id) {
        agreementService.confirmAgreementByManager(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> blockAgreement(@PathVariable(name = "id") Long id) {
        agreementService.blockAgreementByManager(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public AgreementDto getAgreementDtoById(@PathVariable(name = "id") Long id) {
        return agreementService.getAgreementDtoById(id);
    }
}
