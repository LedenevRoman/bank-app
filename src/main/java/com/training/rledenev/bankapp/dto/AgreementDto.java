package com.training.rledenev.bankapp.dto;

import lombok.Data;

@Data
public class AgreementDto {
    private Long id;
    private Double sum;
    private String productType;
    private String currencyCode;
    private Integer periodMonths;
    private String productName;
    private Double interestRate;
}
