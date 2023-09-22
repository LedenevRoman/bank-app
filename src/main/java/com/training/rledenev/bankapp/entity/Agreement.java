package com.training.rledenev.bankapp.entity;

import com.training.rledenev.bankapp.entity.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "period_months")
    private Integer periodMonths;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setProduct(Product product) {
        this.product = product;
        product.addAgreement(this);
    }

    public void setAccount(Account account) {
        this.account = account;
        account.setAgreement(this);
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
