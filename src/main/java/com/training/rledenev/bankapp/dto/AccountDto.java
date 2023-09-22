package com.training.rledenev.bankapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDto {
    private String number;
    private String owner;
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
