package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/transaction")
public class TransactionController {
    private final TransactionService transactionService;

}
