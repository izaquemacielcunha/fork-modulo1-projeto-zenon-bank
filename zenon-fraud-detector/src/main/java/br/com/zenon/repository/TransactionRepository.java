package br.com.zenon.repository;

import java.util.Optional;

import br.com.zenon.model.Transaction;

public interface TransactionRepository {
    Optional<Transaction> findByOriginName(String originName);
}
