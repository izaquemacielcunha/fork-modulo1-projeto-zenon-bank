package br.com.zenon;

import br.com.zenon.model.Statistics;
import br.com.zenon.service.TransactionIngestorReport;
import br.com.zenon.validation.TransactionValidator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    void main(String[] args) {
        String language = args.length > 0 ? args[0] : "pt";
        Locale locale = Locale.of(language);

        NumberFormat integerFormatter = NumberFormat.getIntegerInstance(locale);
        NumberFormat currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
        currencyFormatter.setCurrency(Currency.getInstance("USD"));

        ResourceBundle resourceBundle = ResourceBundle.getBundle("report", locale);

        TransactionIngestorReport transactionReport = new TransactionIngestorReport(new TransactionValidator());
        Statistics statistics = transactionReport.generateStatistics("data/PS_20174392719_1491204439457_log.csv");

        String msgTotalTransactions = resourceBundle.getString("label.cabecalho.total.transaction");
        String msgTotalFrauds = resourceBundle.getString("label.cabecalho.total.fraude");
        String msgTotalAmount = resourceBundle.getString("label.cabecalho.valor.total");

        String totalTransactions = integerFormatter.format(statistics.totalTransactions());
        String totalFrauds = integerFormatter.format(statistics.totalFrauds());
        String totalAmount = currencyFormatter.format(statistics.totalAmount());

        IO.println("""
                %s: %s
                %s: %s
                %s: %s
                """
                .formatted(
                        msgTotalTransactions, totalTransactions,
                        msgTotalFrauds, totalFrauds,
                        msgTotalAmount, totalAmount
                ));
    }
}
