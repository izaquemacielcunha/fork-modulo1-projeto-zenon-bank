package br.com.zenon;

import br.com.zenon.model.Statistics;
import br.com.zenon.service.TransactionIngestorReport;
import br.com.zenon.validation.TransactionValidator;

public class ReportMain {

    void main(String[] args) {
        TransactionIngestorReport transactionReport = new TransactionIngestorReport(new TransactionValidator());
        Statistics statistics = transactionReport.generateStatistics("data/PS_20174392719_1491204439457_log.csv");
        IO.println("""
                Total de linhas: %d
                Total de frauds: %d
                Valor total transactions: %.2f
                """
                .formatted(statistics.totalTransactions(), statistics.totalFrauds(), statistics.totalAmount()));
    }
}
