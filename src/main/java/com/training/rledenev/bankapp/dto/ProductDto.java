package com.training.rledenev.bankapp.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String type;
    private Integer minLimit;
    private Double interestRate;
    private Integer periodMonths;
}
