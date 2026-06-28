package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.zenon.model.Customer;
import br.com.zenon.model.FraudAnalyzer;
import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionType;
import br.com.zenon.service.TransactionIngestor;
import br.com.zenon.service.TransactionIngestorImpl;
import br.com.zenon.validation.TransactionValidator;

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
        TransactionIngestor ingestor = new TransactionIngestorImpl(new TransactionValidator());
        List<Transaction> transactions = ingestor.ingest("data/PS_20174392719_1491204439457_log.csv");
        transactions.stream().limit(10).forEach(IO::println);

        IO.println("-------Bad transactions from csv file---------");
        List<Transaction> badTransactions = ingestor.ingest("data/paysim_with_bad_data.csv");
        badTransactions.stream().limit(10).forEach(IO::println);

        IO.println("-------FraudAnalyzer---------");
        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(transactions);

        List<Transaction> fraudsTransactions = fraudAnalyzer.fraudTransactions();
        IO.println("Total de Fraudes: " + fraudsTransactions.size());

        List<Transaction> highestFraudTransactions = fraudAnalyzer.highestFraudTransactions(3);
        IO.println("Top 3 Fraudes de Maior Valor: ");
        highestFraudTransactions.stream().
                map(Transaction::amount)
                .forEach(amount -> IO.println("%.2f".formatted(amount)));

        IO.println("Clientes Suspeitos: ");
        List<Transaction> highestSuspiciousFraudTransactions = fraudAnalyzer.highestFraudTransactions(5);
        highestSuspiciousFraudTransactions.stream()
                .map(Transaction::origin)
                .map(Customer::name)
                .distinct()
                .forEach(IO::println);

        BigDecimal prejuizo = fraudsTransactions.stream()
                .map(Transaction::amount)
                .reduce(BigDecimal::add)
                .get();
        IO.println("Prejuízo Total: " + prejuizo);

        Map<TransactionType, Long> fraudsByType = fraudsTransactions.stream()
                        .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
        IO.println("Fraudes por Tipo:");
        fraudsByType.entrySet().forEach(IO::println);
    }

}