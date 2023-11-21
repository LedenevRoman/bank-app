package com.training.rledenev.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDto {
    private String number;
    private String owner;
    private String manager;
    private String productName;
    private Double interestRate;
    private LocalDate startDate;
    private LocalDate paymentTerm;
    private String type;
    private String status;
    private Double balance;
    private String currencyCode;
    private String currencyName;
}
