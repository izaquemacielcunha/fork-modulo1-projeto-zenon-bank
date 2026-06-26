package br.com.zenon.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import br.com.zenon.model.Customer;
import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionType;

public class TransactionIngestorImpl implements TransactionIngestor {

    @Override
    public List<Transaction> ingest(String fileName) {
        Path path = Paths.get(fileName);

        try {
            List<String> csvLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            return csvLines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::buildTransactionFromCsvLine)
                    .toList();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> ingestOld(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        Path path = Paths.get(fileName);

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ByteArrayOutputStream lineBuffer = new ByteArrayOutputStream();
            Integer counter = 0;

            goOutFromLoop:
            while (channel.read(buffer) != -1) {
                buffer.flip();

                while (buffer.hasRemaining()) {
                    if(counter == 1001) {
                        break goOutFromLoop;
                    }

                    byte character = buffer.get();

                    if (character == '\n') {
                        counter++;
                        String csvLine = lineBuffer.toString(StandardCharsets.UTF_8);
                        if (!csvLine.contains("step")) {
                            transactions.add(buildTransactionFromCsvLine(csvLine));
                        }
                        lineBuffer.reset();
                    } else {
                        lineBuffer.write(character);
                    }
                }

                buffer.clear();
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    private Transaction buildTransactionFromCsvLine(String csvLine) {
        String[] csvValues = csvLine.split(",");

        return new Transaction(
                Integer.parseInt(csvValues[0]),
                TransactionType.valueOf(csvValues[1]),
                new BigDecimal(csvValues[2]),
                new Customer(csvValues[3], new BigDecimal(csvValues[4]), new BigDecimal(csvValues[5])),
                new Customer(csvValues[6], new BigDecimal(csvValues[7]), new BigDecimal(csvValues[8])),
                "1".equals(csvValues[9]),
                "1".equals(csvValues[10])
        );

    }
}