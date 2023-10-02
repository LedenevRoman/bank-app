package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import com.training.rledenev.bankapp.entity.enums.Status;
import com.training.rledenev.bankapp.exceptions.AgreementNotFoundException;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import com.training.rledenev.bankapp.mapper.AgreementMapper;
import com.training.rledenev.bankapp.provider.UserProvider;
import com.training.rledenev.bankapp.repository.AccountRepository;
import com.training.rledenev.bankapp.repository.AgreementRepository;
import com.training.rledenev.bankapp.repository.ProductRepository;
import com.training.rledenev.bankapp.services.AccountService;
import com.training.rledenev.bankapp.services.AgreementService;
import com.training.rledenev.bankapp.services.TransactionService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgreementServiceImpl implements AgreementService {
    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;
    private final UserProvider userProvider;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AgreementServiceImpl(AgreementRepository agreementRepository, AgreementMapper agreementMapper,
                                UserProvider userProvider, ProductRepository productRepository,
                                AccountRepository accountRepository, AccountService accountService, TransactionService transactionService) {
        this.agreementRepository = agreementRepository;
        this.agreementMapper = agreementMapper;
        this.userProvider = userProvider;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Transactional
    @Override
    public AgreementDto createNewAgreement(AgreementDto agreementDto) {
        Agreement agreement = getNewAgreement(agreementDto);
        Optional<Product> productOptional = productRepository.findActiveProductByName(agreementDto.getProductName());
        agreement.setProduct(productOptional.orElseThrow(() -> new ProductNotFoundException("Product not found")));

        Account account = getNewAccount(agreementDto);
        account.setAgreement(agreement);

        accountRepository.save(account);
        agreement.setAccount(account);
        agreementRepository.save(agreement);
        return agreementMapper.mapToDto(agreement);
    }

    @Transactional
    @Override
    public List<AgreementDto> getAgreementsForManager() {
        return agreementMapper.mapToListDtos(agreementRepository.findAllNewAgreements());
    }

    @Transactional
    @Override
    public void confirmAgreementByManager(Long agreementId) {
        Agreement agreement = getAndUpdateAgreement(agreementId);
        agreement.setStatus(Status.ACTIVE);
        agreement.setStartDate(LocalDate.now());

        Account account = agreement.getAccount();
        if (agreement.getProduct().getType() == ProductType.LOAN
                || agreement.getProduct().getType() == ProductType.CREDIT_CARD) {
            transactionService.giveCreditFundsToAccount(account, agreement.getSum());
        } else {
            account.setBalance(agreement.getSum());
        }
        account.setUpdatedAt(LocalDateTime.now());
        account.setStatus(Status.ACTIVE);

        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void blockAgreementByManager(Long agreementId) {
        Agreement agreement = getAndUpdateAgreement(agreementId);
        agreement.setStatus(Status.BLOCKED);

        Account account = agreement.getAccount();
        account.setUpdatedAt(LocalDateTime.now());
        account.setStatus(Status.BLOCKED);

        accountRepository.save(account);
    }

    @Transactional
    @Override
    public AgreementDto getAgreementDtoById(Long id) {
        return agreementMapper.mapToDto(findById(id));
    }

    private Agreement getAndUpdateAgreement(Long agreementId) {
        Agreement agreement = findById(agreementId);
        agreement.setManager(userProvider.getCurrentUser());
        agreement.setUpdatedAt(LocalDateTime.now());
        return agreement;
    }

    private Agreement findById(Long id) {
        return agreementRepository.findById(id)
                .orElseThrow(() -> new AgreementNotFoundException("Agreement not found with id = " + id));
    }


    private Agreement getNewAgreement(AgreementDto agreementDto) {
        Agreement agreement = agreementMapper.mapToEntity(agreementDto);
        agreement.setStatus(Status.NEW);
        agreement.setCreatedAt(LocalDateTime.now());
        agreement.setUpdatedAt(LocalDateTime.now());
        return agreement;
    }

    private Account getNewAccount(AgreementDto agreementDto) {
        Account account = new Account();
        account.setClient(userProvider.getCurrentUser());
        String number = RandomStringUtils.randomNumeric(16);
        while (accountService.checkAccountNumberExists(number)) {
            number = RandomStringUtils.randomNumeric(16);
        }
        account.setNumber(number);
        account.setStatus(Status.NEW);
        account.setCurrencyCode(CurrencyCode.valueOf(agreementDto.getCurrencyCode()));
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return account;
    }
}
