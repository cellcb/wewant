package me.cell.wewant.core;

import lombok.Value;

@Value(staticConstructor = "of")
public class Item {
    private String name;
    private String type;
    private String url;
    private Integer expect;
    private Boolean proxy;
}
