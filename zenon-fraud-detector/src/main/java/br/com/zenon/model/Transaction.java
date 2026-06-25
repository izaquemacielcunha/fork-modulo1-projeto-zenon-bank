package br.com.zenon.model;

import java.math.BigDecimal;

public record Transaction(
        Integer step,
        TransactionType type,
        BigDecimal amount,
        String nameOrig,
        BigDecimal oldbalanceOrg,
        BigDecimal newbalanceOrig,
        String nameDest,
        BigDecimal oldlanceDest,
        BigDecimal newbalanceDest,
        Integer isFraud,
        Integer isFlaggedFraud
) { }