package br.com.zenon.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import br.com.zenon.db.ConnectionFactory;
import br.com.zenon.model.Customer;
import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionType;

public class TransactioSqlRepositoryImpl implements TransactionRepository {

    @Override
    public Optional<Transaction> findByOriginName(String originName) {
        String query = """
                SELECT  step,
                        type,
                        amount,
                        name_origin,
                        old_balance_origin,
                        new_balance_origin,
                        name_recipient,
                        old_balance_recipient,
                        new_balance_recipient,
                        is_fraud,
                        is_flagged_fraud
                FROM    transactions
                WHERE   name_origin = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, originName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Transaction transaction = mapResultToTransaction(resultSet);
                    return Optional.of(transaction);
                }
            }
        }
        catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar transaction: " + originName, exception);
        }

        return Optional.empty();
    }

    @Override
    public void save(Transaction transaction) {
        String query = """
                INSERT INTO transactions
                        (step,
                        type,
                        amount,
                        name_origin,
                        old_balance_origin,
                        new_balance_origin,
                        name_recipient,
                        old_balance_recipient,
                        new_balance_recipient,
                        is_fraud,
                        is_flagged_fraud)
               VALUES   (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
               """;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, transaction.step());
            preparedStatement.setString(2, transaction.type().name());
            preparedStatement.setBigDecimal(3, transaction.amount());
            preparedStatement.setString(4, transaction.origin().name());
            preparedStatement.setBigDecimal(5, transaction.origin().oldBalance());
            preparedStatement.setBigDecimal(6, transaction.origin().newBalance());
            preparedStatement.setString(7, transaction.recipient().name());
            preparedStatement.setBigDecimal(8, transaction.recipient().oldBalance());
            preparedStatement.setBigDecimal(9, transaction.recipient().newBalance());
            preparedStatement.setBoolean(10, transaction.isFraud());
            preparedStatement.setBoolean(11, transaction.isFlaggedFraud());

            preparedStatement.execute();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Erro ao salvar transaction: " + transaction.origin().name(), exception);
        }
    }

    private Transaction mapResultToTransaction(ResultSet resultSet) {

        try {
            Integer step = resultSet.getInt("step");
            TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
            BigDecimal amount = resultSet.getBigDecimal("amount");
            Customer origin = new Customer(
                    resultSet.getString("name_origin"),
                    resultSet.getBigDecimal("old_balance_origin"),
                    resultSet.getBigDecimal("new_balance_origin")
            );
            Customer recipient = new Customer(
                    resultSet.getString("name_recipient"),
                    resultSet.getBigDecimal("old_balance_recipient"),
                    resultSet.getBigDecimal("new_balance_recipient")
            );
            Boolean isFraud = resultSet.getBoolean("is_fraud");
            Boolean isFlaggedFraud = resultSet.getBoolean("is_flagged_fraud");

            return new Transaction(
                    step,
                    type,
                    amount,
                    origin,
                    recipient,
                    isFraud,
                    isFlaggedFraud
            );
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
