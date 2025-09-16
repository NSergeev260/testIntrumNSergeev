package com.example.testNSergeev.persistence.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import com.example.testNSergeev.usecasses.dto.OperationType;

import jakarta.persistence.*;
import lombok.*;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wallet")
public class WalletEntity {

    @Id
    @Column(name = "wallet_id", unique = true, nullable = false)
    private UUID walletId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "last_operation", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType lastOperation;

    @Column(name = "amount_of_operation", nullable = false)
    @Builder.Default
    private BigDecimal amountOfOperation = BigDecimal.ZERO;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WalletEntity that = (WalletEntity) o;
        return Objects.equals(walletId, that.walletId) &&
                Objects.equals(balance, that.balance) &&
                lastOperation == that.lastOperation &&
                Objects.equals(amountOfOperation, that.amountOfOperation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, balance, lastOperation, amountOfOperation);
    }

    @Override
    public String toString() {
        return "WalletEntity{" +
                "walletId=" + walletId +
                ", balance=" + balance +
                ", lastOperation=" + lastOperation +
                ", amountOfOperation=" + amountOfOperation +
                '}';
    }
}
