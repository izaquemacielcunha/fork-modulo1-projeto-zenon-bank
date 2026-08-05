package br.com.zenon;

import br.com.zenon.model.Customer;
import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionType;
import br.com.zenon.repository.TransactioSqlRepositoryImpl;
import br.com.zenon.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class DBMain {

    void main(String[] args) {

        TransactionRepository repository = new TransactioSqlRepositoryImpl();

        Optional<Transaction> transaction = repository.findByOriginName("C1231006815");

        transaction.ifPresentOrElse(IO::println, () -> IO.println("Transaction not found: C1231006815"));

        // New transaction
        Customer transactionCustomerOrig = new Customer("C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0"));
        Customer transactionCustomerDest = new Customer("C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63"));

        Transaction newTransaction = new Transaction(
                743,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                transactionCustomerOrig,
                transactionCustomerDest,
                Boolean.TRUE,
                Boolean.FALSE
        );

        repository.save(newTransaction);
    }
}
