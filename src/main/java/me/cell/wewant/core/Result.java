package me.cell.wewant.core;

import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class Result {
    private String stockStatus;
    private BigDecimal price;
}
