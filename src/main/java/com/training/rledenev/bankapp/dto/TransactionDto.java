package com.training.rledenev.bankapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {
    private String debitAccountNumber;
    private String creditAccountNumber;
    private Double amount;
    private String currencyCode;
    private Double debitBalanceDifference;
    private Double creditBalanceDifference;
    private String type;
    private String description;
    private Date createdAt;
}
