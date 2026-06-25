package br.com.zenon.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionType;

public class TransactionIngestorImpl implements TransactionIngestor {

    @Override
    public List<Transaction> ingest(String fileName) {
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
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return transactions;
    }

    private Transaction buildTransactionFromCsvLine(String csvLine) {
        String[] csvValues = csvLine.split(",");

        return new Transaction(
                Integer.parseInt(csvValues[0]),
                TransactionType.valueOf(csvValues[1]),
                BigDecimal.valueOf(Double.parseDouble(csvValues[2])),
                csvValues[3],
                BigDecimal.valueOf(Double.parseDouble(csvValues[4])),
                BigDecimal.valueOf(Double.parseDouble(csvValues[5])),
                csvValues[6],
                BigDecimal.valueOf(Double.parseDouble(csvValues[7])),
                BigDecimal.valueOf(Double.parseDouble(csvValues[8])),
                Integer.parseInt(csvValues[9]),
                Integer.parseInt(csvValues[10])
        );

    }
}