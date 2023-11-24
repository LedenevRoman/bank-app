package com.training.rledenev.dto;

import lombok.Data;

@Data
public class AgreementDto {
    private Long id;
    private Double sum;
    private String productType;
    private String currencyCode;
    private String status;
    private Double interestRate;
    private Integer periodMonths;
    private String productName;
}
