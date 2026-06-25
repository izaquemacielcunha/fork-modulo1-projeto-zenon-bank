package br.com.zenon.model;

import java.math.BigDecimal;

public record Customer(
        String name,
        BigDecimal balance
) { }