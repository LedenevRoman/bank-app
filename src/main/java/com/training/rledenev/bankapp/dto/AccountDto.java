package com.training.rledenev.bankapp.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String number;
    private String type;
    private String status;
    private Double balance;
    private String currencyCode;
}
