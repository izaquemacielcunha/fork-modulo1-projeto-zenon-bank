package br.com.zenon.model;

import java.util.Arrays;

public enum TransactionType {
    CASH_IN,
    CASH_OUT,
    DEBIT,
    PAYMENT,
    TRANSFER;

    public static boolean isValidEnum(String value) {
        return Arrays.stream(TransactionType.values())
                .anyMatch(type -> type.name().equals(value));
    }
}
