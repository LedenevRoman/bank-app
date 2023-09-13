package com.training.rledenev.bankapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AccountDto {
    private String name;
    private String type;
    private String status;
    private Double balance;
    private String currencyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(type, that.type)
                && Objects.equals(status, that.status)
                && Objects.equals(balance, that.balance)
                && Objects.equals(currencyCode, that.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, status, balance, currencyCode);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "name='" + name + '\'' +
                ", accountType=" + type +
                ", status=" + status +
                ", balance=" + balance +
                ", currencyCode=" + currencyCode +
                '}';
    }
}
