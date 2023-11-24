package com.training.rledenev.service.impl;

import com.training.rledenev.dto.TransactionDto;
import com.training.rledenev.entity.Account;
import com.training.rledenev.entity.Transaction;
import com.training.rledenev.entity.enums.CurrencyCode;
import com.training.rledenev.entity.enums.TransactionType;
import com.training.rledenev.exception.InsufficientFundsException;
import com.training.rledenev.exception.NotOwnerException;
import com.training.rledenev.mapper.TransactionMapper;
import com.training.rledenev.repository.AccountRepository;
import com.training.rledenev.repository.TransactionRepository;
import com.training.rledenev.security.UserProvider;
import com.training.rledenev.service.AccountService;
import com.training.rledenev.service.CurrencyService;
import com.training.rledenev.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final AccountRepository accountRepository;
    private final UserProvider userProvider;

    @Transactional
    @Override
    public List<TransactionDto> getAllTransactionsOfAccount(String accountNumber) {
        return transactionMapper.mapToListDto(transactionRepository
                .getAllTransactionsWithAccountNumber(accountNumber));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void createTransaction(TransactionDto transactionDto) {
        Account debitAccount = accountService.getAccountByNumber(transactionDto.getDebitAccountNumber());
        checkDebitAccountOwner(debitAccount);
        Account creditAccount = accountService.getAccountByNumber(transactionDto.getCreditAccountNumber());
        Transaction transaction = saveTransactionFromDto(transactionDto, debitAccount, creditAccount);
        updateAccount(debitAccount, debitAccount.getBalance().subtract(transaction.getDebitBalanceDifference()));
        updateAccount(creditAccount, creditAccount.getBalance().add(transaction.getCreditBalanceDifference()));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void giveCreditFundsToAccount(Account creditAccount, BigDecimal amount) {
        Account debitAccount = accountService.getMainBankAccount();
        Transaction transaction = saveNewTransaction(creditAccount, amount, debitAccount);
        updateAccount(debitAccount, debitAccount.getBalance().subtract(transaction.getDebitBalanceDifference()));
        updateAccount(creditAccount, transaction.getCreditBalanceDifference());
    }

    private Transaction saveNewTransaction(Account creditAccount, BigDecimal amount, Account debitAccount) {
        Transaction transaction = getNewTransaction(creditAccount, amount);
        setTransactionData(debitAccount, creditAccount, transaction);
        transaction.setCreditBalanceDifference(amount);
        transactionRepository.save(transaction);
        return transaction;
    }

    private Transaction saveTransactionFromDto(TransactionDto transactionDto, Account debitAccount,
                                               Account creditAccount) {
        Transaction transaction = transactionMapper.mapToEntity(transactionDto);
        setTransactionData(debitAccount, creditAccount, transaction);
        BigDecimal creditBalanceDifference = calculateBalanceDifference(transaction.getAmount(),
                transaction.getCurrencyCode(), creditAccount.getCurrencyCode());
        transaction.setCreditBalanceDifference(creditBalanceDifference);
        transactionRepository.save(transaction);
        return transaction;
    }

    private void updateAccount(Account account, BigDecimal balance) {
        account.setBalance(balance);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);
    }

    private void setTransactionData(Account debitAccount, Account creditAccount, Transaction transaction) {
        BigDecimal debitBalanceDifference = calculateBalanceDifference(transaction.getAmount(),
                transaction.getCurrencyCode(), debitAccount.getCurrencyCode());
        if (debitBalanceDifference.compareTo(debitAccount.getBalance()) > 0) {
            throw new InsufficientFundsException("Not enough money");
        }
        transaction.setDebitBalanceDifference(debitBalanceDifference);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
    }

    private static Transaction getNewTransaction(Account account, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCurrencyCode(account.getCurrencyCode());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setDescription("Credit funds");
        return transaction;
    }

    private BigDecimal calculateBalanceDifference(BigDecimal amount, CurrencyCode transactionCurrency,
                                                  CurrencyCode accountCurrency) {
        BigDecimal rateTransactionCurrency = currencyService.getRateOfCurrency(transactionCurrency.toString());
        BigDecimal rateAccountCurrency = currencyService.getRateOfCurrency(accountCurrency.toString());
        BigDecimal rateOfConversion = rateTransactionCurrency.divide(rateAccountCurrency, 4, RoundingMode.HALF_UP);
        return amount.multiply(rateOfConversion);
    }

    private void checkDebitAccountOwner(Account debitAccount) {
        if (!debitAccount.getClient().equals(userProvider.getCurrentUser())) {
            throw new NotOwnerException("Access Denied, wrong account owner");
        }
    }
}
