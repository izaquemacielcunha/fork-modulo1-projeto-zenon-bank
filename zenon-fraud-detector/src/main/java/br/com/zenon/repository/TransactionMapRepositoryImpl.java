package br.com.zenon.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.zenon.model.Transaction;

public class TransactionMapRepositoryImpl implements TransactionRepository {

    private final Map<String, Transaction> transactionsMap;

    public TransactionMapRepositoryImpl(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactionsMap = toMap(transactions);
    }

    @Override
    public Optional<Transaction> findByOriginName(String originName) {
        if (transactionsMap.containsKey(originName)) {
            return Optional.of(transactionsMap.get(originName));
        }
        return Optional.empty();
    }

    @Override
    public void save(Transaction transaction) {
        this.transactionsMap.putIfAbsent(transaction.origin().name(), transaction);
    }

    private Map<String, Transaction> toMap(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.toMap(trans -> trans.origin().name(), Function.identity()));
    }
}
