package br.com.zenon.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.com.zenon.model.Transaction;

public class TransactionListRepositoryImpl implements TransactionRepository {
    private final List<Transaction> transactions;

    public TransactionListRepositoryImpl(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> findByOriginName(String originName) {
        Transaction tran = transactions.stream()
                .filter(transaction -> transaction.origin().name().equals(originName))
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(tran);
    }
}
