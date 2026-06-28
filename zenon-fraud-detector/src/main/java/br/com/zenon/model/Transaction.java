package br.com.zenon.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(
        Integer step,
        TransactionType type,
        BigDecimal amount,
        Customer origin,
        Customer recipient,
        Boolean isFraud,
        Boolean isFlaggedFraud
) {
    public Transaction {
        Objects.requireNonNull(step, "Step should not be null");
        Objects.requireNonNull(type, "Type should not be null");
        Objects.requireNonNull(amount, "Amount should not be null");
        Objects.requireNonNull(origin, "Origin should not be null");
        Objects.requireNonNull(recipient, "Recipient should not be null");
        Objects.requireNonNull(isFraud, "Is fraud should not be null");
        Objects.requireNonNull(isFlaggedFraud, "Is flagged fraud should not be null");

        if (step <= 0) throw new IllegalArgumentException("Step should be positive: " + step);
        if (amount.signum() < 0) throw new IllegalArgumentException("Amount should not be negative: " + amount);
    }
}