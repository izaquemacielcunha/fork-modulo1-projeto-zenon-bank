package br.com.zenon.model;

import java.math.BigDecimal;

public record Transaction(
        Integer step,
        TransactionType type,
        BigDecimal amount,
        Customer origin,
        Customer recipient,
        Boolean isFraud,
        Boolean isFlaggedFraud
) { }