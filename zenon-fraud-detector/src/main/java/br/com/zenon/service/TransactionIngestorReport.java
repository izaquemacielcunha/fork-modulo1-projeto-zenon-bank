package br.com.zenon.service;

import br.com.zenon.model.Statistics;
import br.com.zenon.model.TransactionReport;
import br.com.zenon.validation.TransactionValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionIngestorReport {
    private final TransactionValidator transactionValidator;

    public TransactionIngestorReport(TransactionValidator transactionValidator) {
        this.transactionValidator = transactionValidator;
    }

    public Statistics generateStatistics(String fileName) {
        Path path = Paths.get(fileName);
        try (Stream<String> linhas = Files.lines(path, StandardCharsets.UTF_8)) {
            return linhas
                    .skip(1)
                    .map(this::buildTransactionReportFromCsvLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(Statistics.ZERO, Statistics::addTransactionReport, Statistics::add);
        }
        catch (IOException e) {
            throw new RuntimeException("Error while reading the file: " + fileName, e);
        }
    }

    private Optional<TransactionReport> buildTransactionReportFromCsvLine(String csvLine) {
        try {
            String[] csvValues = csvLine.split(",");

            transactionValidator.validateTransactionFields(csvValues);

            TransactionReport transaction = new TransactionReport(
                    new BigDecimal(csvValues[2]),
                    "1".equals(csvValues[9])
            );
            return Optional.of(transaction);
        }
        catch (Exception e) {
            System.err.println("Error: " + csvLine + " | " + e);
            return Optional.empty();
        }
    }

}
