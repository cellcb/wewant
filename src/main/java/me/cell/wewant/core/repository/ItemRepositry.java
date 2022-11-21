package me.cell.wewant.core.repository;

import me.cell.wewant.core.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepositry extends JpaRepository<Item,Long> {
    List<Item> findByOrderByIdDesc();
}
