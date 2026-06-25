package br.com.zenon;

import java.math.BigDecimal;

import br.com.zenon.model.Customer;
import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionType;

public class App {
    void main(String[] args) {

        // Transaction 1
        Customer transactionOneCustomerOrig = new Customer("C1231006815", BigDecimal.valueOf(170136.0));
        Customer transactionOneCustomerDest = new Customer("M1979787155", BigDecimal.valueOf(0.0));

        BigDecimal transactionOneAmount = BigDecimal.valueOf(9839.64);
        Transaction transactionOne = new Transaction(
                1,
                TransactionType.PAYMENT,
                transactionOneAmount,
                transactionOneCustomerOrig.name(),
                transactionOneCustomerOrig.balance(),
                transactionOneCustomerOrig.balance().subtract(transactionOneAmount), // Isso aqui altera direto no customer ou apenas retorna o valor?
                transactionOneCustomerDest.name(),
                transactionOneCustomerDest.balance(),
                transactionOneCustomerDest.balance(),
                0,
                0
        );

        // Transaction 2
        Customer transactionTwoCustomerOrig = new Customer("C1280323807", BigDecimal.valueOf(850002.52));
        Customer transactionTwoCustomerDest = new Customer("C873221189", BigDecimal.valueOf(6510099.11));

        BigDecimal transactionTwoAmount = BigDecimal.valueOf(850002.52);
        Transaction transactionTwo = new Transaction(
                743,
                TransactionType.CASH_OUT,
                transactionTwoAmount,
                transactionTwoCustomerOrig.name(),
                transactionTwoCustomerOrig.balance(),
                transactionTwoCustomerOrig.balance().subtract(transactionTwoAmount), // Isso aqui altera direto no customer ou apenas retorna o valor?
                transactionTwoCustomerDest.name(),
                transactionTwoCustomerDest.balance(),
                transactionTwoCustomerDest.balance().add(transactionTwoAmount),
                1,
                0
        );

        IO.println("Transação 1: " + transactionOne.toString());
        IO.println("Transação 1: " + transactionTwo.toString());
    }
}