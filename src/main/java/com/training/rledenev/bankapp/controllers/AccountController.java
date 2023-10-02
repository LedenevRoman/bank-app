package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all/client")
    public ResponseEntity<List<AccountDto>> getAccountsForClient() {
        List<AccountDto> accountDtos = accountService.getAccountsForClient();
        return ResponseEntity.ok().body(accountDtos);
    }
}
