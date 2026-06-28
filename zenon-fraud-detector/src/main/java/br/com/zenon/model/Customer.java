package br.com.zenon.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Customer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance
) {
    public Customer {
        Objects.requireNonNull(name, "Name should not be null");
        Objects.requireNonNull(oldBalance, "Old balance should not be null");
        Objects.requireNonNull(newBalance, "New balance should not be null");

        if (name.trim().isEmpty()) throw new IllegalArgumentException("Name should not be empty");
        if (oldBalance.signum() < 0) throw new IllegalArgumentException("Old balance should not be negative: " + oldBalance);
        if (newBalance.signum() < 0) throw new IllegalArgumentException("New balance should not be negative: " + newBalance);
    }
}