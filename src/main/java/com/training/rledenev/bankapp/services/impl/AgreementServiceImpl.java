package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import com.training.rledenev.bankapp.entity.enums.Status;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import com.training.rledenev.bankapp.mapper.AgreementMapper;
import com.training.rledenev.bankapp.provider.UserProvider;
import com.training.rledenev.bankapp.repository.AccountRepository;
import com.training.rledenev.bankapp.repository.AgreementRepository;
import com.training.rledenev.bankapp.repository.ProductRepository;
import com.training.rledenev.bankapp.services.AgreementService;
import com.training.rledenev.bankapp.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.training.rledenev.bankapp.services.impl.ServiceUtils.getEnumName;

@Service
public class AgreementServiceImpl implements AgreementService {
    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;
    private final UserProvider userProvider;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final ProductService productService;

    public AgreementServiceImpl(AgreementRepository agreementRepository, AgreementMapper agreementMapper,
                                UserProvider userProvider, ProductRepository productRepository,
                                AccountRepository accountRepository, ProductService productService) {
        this.agreementRepository = agreementRepository;
        this.agreementMapper = agreementMapper;
        this.userProvider = userProvider;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.productService = productService;
    }

    @Transactional
    @Override
    public Agreement createNewAgreement(AgreementDto agreementDto) {
        Agreement agreement = getNewAgreement(agreementDto);
        Optional<Product> productOptional = productRepository.findById(agreementDto.getProductId());
        agreement.setProduct(productOptional.orElseThrow(() -> new ProductNotFoundException("Product not found")));

        Account account = getNewAccount(agreementDto);
        account.setBalance(agreement.getSum());
        account.setAgreement(agreement);
        account.setClient(userProvider.getCurrentUser());

        accountRepository.save(account);
        agreement.setAccount(account);
        agreementRepository.save(agreement);
        return agreement;
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
        account.setName(UUID.randomUUID().toString());
        account.setType(ProductType.valueOf(getEnumName(agreementDto.getProductType())));
        account.setStatus(Status.NEW);
        account.setCurrencyCode(CurrencyCode.valueOf(agreementDto.getCurrencyCode()));
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return account;
    }
}
