package com.training.rledenev.bankapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgreementDto {
    private Double sum;
    private String productType;
    private String currencyCode;
    private Integer periodMonths;
    private Long productId;
}
