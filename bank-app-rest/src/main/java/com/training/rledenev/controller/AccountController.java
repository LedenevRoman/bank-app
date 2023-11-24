package com.training.rledenev.controller;

import com.training.rledenev.dto.AccountDto;
import com.training.rledenev.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/all/client")
    public ResponseEntity<List<AccountDto>> getAccountsForClient() {
        List<AccountDto> accountDtos = accountService.getAccountsForClient();
        return ResponseEntity.ok().body(accountDtos);
    }
}
