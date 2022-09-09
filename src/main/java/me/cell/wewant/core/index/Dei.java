package me.cell.wewant.core.index;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Dei {
    private String url;
    private String name;
    private BigDecimal price;
    private String stockStatus;
    private String catchTime;
}
