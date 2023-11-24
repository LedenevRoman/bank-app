package com.training.rledenev.service;

import com.training.rledenev.dto.TransactionDto;
import com.training.rledenev.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAllTransactionsOfAccount(String accountNumber);

    void createTransaction(TransactionDto transactionDto);

    void giveCreditFundsToAccount(Account account, BigDecimal amount);
}
