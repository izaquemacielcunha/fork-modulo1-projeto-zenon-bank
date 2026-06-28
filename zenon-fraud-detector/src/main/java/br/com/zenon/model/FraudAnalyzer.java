package br.com.zenon.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public List<Transaction> fraudTransactions() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .toList();
    }

    public List<Transaction> highestFraudTransactions(Integer limit) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(limit)
                .toList();
    }
}
