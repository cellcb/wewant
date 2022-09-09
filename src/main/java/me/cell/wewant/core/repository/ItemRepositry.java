package me.cell.wewant.core.repository;

import me.cell.wewant.core.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositry extends JpaRepository<Item,Long> {
}
