package br.com.zenon.validation;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.zenon.model.TransactionType;

public class TransactionValidator {

    public void validateTransactionFields(String[] csvValues) {

        Optional<String> step = OptionalOfStringNullAndEmptyValidation(csvValues[0]);
        if (step.isEmpty()) {
            throw new IllegalArgumentException("Step should not be null or empty");
        }
        if (Integer.parseInt(step.get()) <= 0) {
            throw new IllegalArgumentException("Step should be positive: " + step.get());
        }

        Optional<String> type = OptionalOfStringNullAndEmptyValidation(csvValues[1]);
        if (type.isEmpty()) {
            throw new IllegalArgumentException("Type should not be null or empty");
        }
        if (!TransactionType.isValidEnum(type.get())) {
            throw new IllegalArgumentException("Type should be a valid value: " + type.get());
        }

        Optional<String> amount = OptionalOfStringNullAndEmptyValidation(csvValues[2]);
        if (amount.isEmpty()) {
            throw new IllegalArgumentException("Amount should not be null or empty");
        }
        if (new BigDecimal(amount.get()).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount should not be negative: " + amount.get());
        }

        Optional<String> nameOrigin = OptionalOfStringNullAndEmptyValidation(csvValues[3]);
        Optional<String> oldBalanceOrigin = OptionalOfStringNullAndEmptyValidation(csvValues[4]);
        Optional<String> newBalanceOrigin = OptionalOfStringNullAndEmptyValidation(csvValues[5]);
        if (nameOrigin.isEmpty()) {
            throw new IllegalArgumentException("Name origin should not be null or empty");
        }
        if (oldBalanceOrigin.isEmpty()) {
            throw new IllegalArgumentException("Old balance origin should not be null or empty");
        }
        if (new BigDecimal(oldBalanceOrigin.get()).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Old balance origin should not be negative: " + oldBalanceOrigin.get());
        }
        if (newBalanceOrigin.isEmpty()) {
            throw new IllegalArgumentException("New balance origin should not be null or empty");
        }
        if (new BigDecimal(newBalanceOrigin.get()).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("New balance origin should not be negative: " + newBalanceOrigin.get());
        }

        Optional<String> nameRecipient = OptionalOfStringNullAndEmptyValidation(csvValues[6]);
        Optional<String> oldBalanceRecipient = OptionalOfStringNullAndEmptyValidation(csvValues[7]);
        Optional<String> newBalanceRecipient = OptionalOfStringNullAndEmptyValidation(csvValues[8]);
        if (nameRecipient.isEmpty()) {
            throw new IllegalArgumentException("Name recipient should not be null or empty");
        }
        if (oldBalanceRecipient.isEmpty()) {
            throw new IllegalArgumentException("Old balance recipient should not be null or empty");
        }
        if (new BigDecimal(oldBalanceRecipient.get()).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Old balance recipient should not be negative: " + oldBalanceRecipient.get());
        }
        if (newBalanceRecipient.isEmpty()) {
            throw new IllegalArgumentException("New balance recipient should not be null or empty");
        }
        if (new BigDecimal(newBalanceRecipient.get()).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("New balance recipient should not be negative: " + newBalanceRecipient.get());
        }

        Optional<String> isFraud = OptionalOfStringNullAndEmptyValidation(csvValues[9]);
        Optional<String> isFlaggedFraud = OptionalOfStringNullAndEmptyValidation(csvValues[10]);
        if (isFraud.isEmpty()) {
            throw new IllegalArgumentException("Is fraud should not be null or empty");
        }
        if (!(isFraud.get().equals("0") || isFraud.get().equals("1"))) {
            throw new IllegalArgumentException("Is fraud should be 0 or 1: " + isFraud.get());
        }
        if (isFlaggedFraud.isEmpty()) {
            throw new IllegalArgumentException("Is flagged fraud should not be null or empty");
        }
        if (!(isFlaggedFraud.get().equals("0") || isFlaggedFraud.get().equals("1"))) {
            throw new IllegalArgumentException("Is flagged fraud should be 0 or 1: " + isFlaggedFraud.get());
        }
    }

    private Optional<String> OptionalOfStringNullAndEmptyValidation(String string) {
        return Optional.ofNullable(string).filter(str -> !str.trim().isEmpty());
    }
}
