package com.training.rledenev.bankapp.entity;

import com.training.rledenev.bankapp.entity.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "agreements")
@Getter
@Setter
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Enumerated
    @Column(name = "status")
    private Status status;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setAccount(Account account) {
        this.account = account;
        account.setAgreement(this);
    }

    public Agreement setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public Agreement setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Agreement setSum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    public Agreement setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agreement agreement = (Agreement) o;
        return Objects.equals(id, agreement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                '}';
    }
}
