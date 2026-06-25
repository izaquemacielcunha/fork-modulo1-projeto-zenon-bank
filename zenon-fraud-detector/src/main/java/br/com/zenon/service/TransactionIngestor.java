package br.com.zenon.service;

import java.util.List;

import br.com.zenon.model.Transaction;

public interface TransactionIngestor {

    List<Transaction> ingest(String fileName);
}