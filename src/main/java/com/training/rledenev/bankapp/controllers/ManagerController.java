package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.ManagerDto;
import com.training.rledenev.bankapp.entity.Manager;
import com.training.rledenev.bankapp.services.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createManager(@RequestBody ManagerDto managerDto) {
        Manager manager = managerService.createManager(managerDto);
        return ResponseEntity.created(URI.create("/" + manager.getId())).body(manager.getId());
    }
}
