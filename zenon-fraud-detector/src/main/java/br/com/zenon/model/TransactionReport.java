package br.com.zenon.model;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionReport(
        BigDecimal amount,
        Boolean isFraud
) {
    public TransactionReport {
        Objects.requireNonNull(amount, "Amount should not be null");
        Objects.requireNonNull(isFraud, "Is fraud should not be null");

        if (amount.signum() < 0) throw new IllegalArgumentException("Amount should not be negative: " + amount);
    }
}
