package com.training.rledenev.bankapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AgreementDto {
    private Double interestRate;
    private String status;
    private Double sum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgreementDto that = (AgreementDto) o;
        return Objects.equals(interestRate, that.interestRate)
                && Objects.equals(status, that.status)
                && Objects.equals(sum, that.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interestRate, status, sum);
    }

    @Override
    public String toString() {
        return "AgreementDto{" +
                "interestRate=" + interestRate +
                ", status=" + status +
                ", sum=" + sum +
                '}';
    }
}
