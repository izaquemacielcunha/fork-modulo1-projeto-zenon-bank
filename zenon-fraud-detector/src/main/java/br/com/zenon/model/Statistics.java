package br.com.zenon.model;

import java.math.BigDecimal;

public record Statistics(
        Long totalTransactions,
        Long totalFrauds,
        BigDecimal totalAmount
) {
    public static final Statistics ZERO = new Statistics(0L, 0L, BigDecimal.ZERO);

    public Statistics addTransactionReport(TransactionReport transactionReport) {
        return new Statistics(
                this.totalTransactions + 1,
                this.totalFrauds + (transactionReport.isFraud() ? 1 : 0),
                this.totalAmount.add(transactionReport.amount())
        );
    }

    public Statistics add(Statistics other) {
        return new Statistics(
                this.totalTransactions + other.totalTransactions,
                this.totalFrauds + other.totalFrauds,
                this.totalAmount.add(other.totalAmount)
        );
    }
}
