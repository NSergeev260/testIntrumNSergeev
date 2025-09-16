package com.example.testNSergeev.persistence.model;

import com.example.testNSergeev.usecasses.dto.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wallet")
public class WalletInfoEntity {

    @Id
    @Column(name = "wallet_id", unique = true, nullable = false)
    private UUID walletId;

    @Column(name = "last_operation", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType lastOperation;

    @Column(name = "amount_of_operation", nullable = false)
    @Builder.Default
    private BigDecimal amountOfOperation = BigDecimal.ZERO;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WalletInfoEntity that = (WalletInfoEntity) o;
        return Objects.equals(walletId, that.walletId) &&
                lastOperation == that.lastOperation &&
                Objects.equals(amountOfOperation, that.amountOfOperation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, lastOperation, amountOfOperation);
    }

    @Override
    public String toString() {
        return "WalletInfoEntity{" +
                "walletId=" + walletId +
                ", lastOperation=" + lastOperation +
                ", amountOfOperation=" + amountOfOperation +
                '}';
    }
}
