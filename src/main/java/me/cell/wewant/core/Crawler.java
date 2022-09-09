package me.cell.wewant.core;

import java.util.Optional;

public interface Crawler {
    Optional<Result> single(String url);

    Optional<Result> list(String url);
}
