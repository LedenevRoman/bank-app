package com.training.rledenev.service.impl;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.entity.Account;
import com.training.rledenev.entity.Agreement;
import com.training.rledenev.entity.Product;
import com.training.rledenev.entity.enums.CurrencyCode;
import com.training.rledenev.entity.enums.ProductType;
import com.training.rledenev.entity.enums.Status;
import com.training.rledenev.exception.AgreementNotFoundException;
import com.training.rledenev.exception.ProductNotFoundException;
import com.training.rledenev.mapper.AgreementMapper;
import com.training.rledenev.repository.AccountRepository;
import com.training.rledenev.repository.AgreementRepository;
import com.training.rledenev.repository.ProductRepository;
import com.training.rledenev.security.UserProvider;
import com.training.rledenev.service.AccountService;
import com.training.rledenev.service.AgreementService;
import com.training.rledenev.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AgreementServiceImpl implements AgreementService {
    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;
    private final UserProvider userProvider;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

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
        while (accountService.isAccountNumberExists(number)) {
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
