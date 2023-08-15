package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.dto.ManagerDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.Manager;
import com.training.rledenev.bankapp.services.AccountService;
import com.training.rledenev.bankapp.services.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> createManager(@RequestBody AccountDto accountDto) {
        Account account = accountService.createAccount(accountDto);
        return ResponseEntity.created(URI.create("/" + account.getId())).body(account.getId());
    }
}
