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
import com.training.rledenev.bankapp.services.ProductService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.training.rledenev.bankapp.services.impl.ServiceUtils.getEnumName;

@Service
public class AgreementServiceImpl implements AgreementService {
    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;
    private final UserProvider userProvider;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final ProductService productService;
    private final AccountService accountService;

    public AgreementServiceImpl(AgreementRepository agreementRepository, AgreementMapper agreementMapper,
                                UserProvider userProvider, ProductRepository productRepository,
                                AccountRepository accountRepository, ProductService productService,
                                AccountService accountService) {
        this.agreementRepository = agreementRepository;
        this.agreementMapper = agreementMapper;
        this.userProvider = userProvider;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.productService = productService;
        this.accountService = accountService;
    }

    @Transactional
    @Override
    public AgreementDto createNewAgreement(AgreementDto agreementDto) {
        Agreement agreement = getNewAgreement(agreementDto);
        Optional<Product> productOptional = productRepository.findActiveProductByName(agreementDto.getProductName());
        agreement.setProduct(productOptional.orElseThrow(() -> new ProductNotFoundException("Product not found")));

        Account account = getNewAccount(agreementDto);
        account.setBalance(agreement.getSum());
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
    public void confirmAgreementByManager(AgreementDto agreementDto) {
        Agreement agreement = getAndUpdateAgreement(agreementDto);
        agreement.setStatus(Status.ACTIVE);
        Account account = getAndUpdateAccount(agreementDto, agreement);
        account.setStatus(Status.ACTIVE);

        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void blockAgreementByManager(AgreementDto agreementDto) {
        Agreement agreement = getAndUpdateAgreement(agreementDto);
        agreement.setStatus(Status.BLOCKED);
        Account account = getAndUpdateAccount(agreementDto, agreement);
        account.setStatus(Status.BLOCKED);

        accountRepository.save(account);
    }

    private Agreement getAndUpdateAgreement(AgreementDto agreementDto) {
        long agreementId = agreementDto.getId();
        Agreement agreement = agreementRepository.findById(agreementId)
                .orElseThrow(() -> new AgreementNotFoundException("Agreement not found with id = " + agreementId));
        agreement.setManager(userProvider.getCurrentUser());
        agreement.setUpdatedAt(LocalDateTime.now());
        return agreement;
    }

    private static Account getAndUpdateAccount(AgreementDto agreementDto, Agreement agreement) {
        Account account = agreement.getAccount();
        account.setBalance(BigDecimal.valueOf(agreementDto.getSum()));
        account.setCurrencyCode(CurrencyCode.valueOf(agreementDto.getCurrencyCode().toUpperCase()));
        account.setUpdatedAt(LocalDateTime.now());
        return account;
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
        account.setType(ProductType.valueOf(getEnumName(agreementDto.getProductType())));
        account.setStatus(Status.NEW);
        account.setCurrencyCode(CurrencyCode.valueOf(agreementDto.getCurrencyCode()));
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return account;
    }
}
