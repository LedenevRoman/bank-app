package com.training.rledenev.controller;

import com.training.rledenev.dto.TransactionDto;
import com.training.rledenev.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactionsOfAccount(@RequestBody String accountNumber) {
        return ResponseEntity.ok(transactionService.getAllTransactionsOfAccount(accountNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.createTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
