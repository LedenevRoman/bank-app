package com.training.rledenev.bankapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
public class AccountDto {
    private String name;
    private String accountType;
    private String status;
    private Double balance;
    private String currencyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(accountType, that.accountType)
                && Objects.equals(status, that.status)
                && Objects.equals(balance, that.balance)
                && Objects.equals(currencyCode, that.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, accountType, status, balance, currencyCode);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "name='" + name + '\'' +
                ", accountType=" + accountType +
                ", status=" + status +
                ", balance=" + balance +
                ", currencyCode=" + currencyCode +
                '}';
    }
}
