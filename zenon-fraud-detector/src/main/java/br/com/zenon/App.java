package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;

import br.com.zenon.model.Customer;
import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionType;
import br.com.zenon.service.TransactionIngestor;
import br.com.zenon.service.TransactionIngestorImpl;

public class App {
    void main(String[] args) {

        // Transaction 1
        Customer transactionOneCustomerOrig = new Customer("C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.36"));
        Customer transactionOneCustomerDest = new Customer("M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0"));

        Transaction transactionOne = new Transaction(
                1,
                TransactionType.PAYMENT,
                new BigDecimal("9839.64"),
                transactionOneCustomerOrig,
                transactionOneCustomerDest,
                Boolean.FALSE,
                Boolean.FALSE
        );

        // Transaction 2
        Customer transactionTwoCustomerOrig = new Customer("C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0"));
        Customer transactionTwoCustomerDest = new Customer("C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63"));

        Transaction transactionTwo = new Transaction(
                743,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                transactionTwoCustomerOrig,
                transactionTwoCustomerDest,
                Boolean.TRUE,
                Boolean.FALSE
        );

        IO.println("Transação 1: " + transactionOne);
        IO.println("Transação 2: " + transactionTwo);

        IO.println("-------Transactions from csv file---------");
        TransactionIngestor ingestor = new TransactionIngestorImpl();
        List<Transaction> transactions = ingestor.ingest("data/PS_20174392719_1491204439457_log.csv");

        transactions.stream().limit(10).forEach(IO::println);
    }
}