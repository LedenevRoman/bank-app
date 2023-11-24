package com.training.rledenev.controller;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/agreement")
public class AgreementController {
    private final AgreementService agreementService;

    @PostMapping("/create")
    public ResponseEntity<Long> createNewAgreement(@RequestBody AgreementDto agreementDto) {
        agreementDto = agreementService.createNewAgreement(agreementDto);
        return ResponseEntity.created(URI.create("/" + agreementDto.getId())).body(agreementDto.getId());
    }

    @GetMapping("/all/new")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<AgreementDto>> getNewAgreements() {
        List<AgreementDto> agreementDtos = agreementService.getAgreementsForManager();
        return ResponseEntity.ok().body(agreementDtos);
    }

    @PutMapping("/confirm/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Void> confirmAgreement(@PathVariable(name = "id") Long id) {
        agreementService.confirmAgreementByManager(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Void> blockAgreement(@PathVariable(name = "id") Long id) {
        agreementService.blockAgreementByManager(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public AgreementDto getAgreementDtoById(@PathVariable(name = "id") Long id) {
        return agreementService.getAgreementDtoById(id);
    }
}
