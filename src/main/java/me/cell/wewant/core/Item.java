package me.cell.wewant.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//@Value(staticConstructor = "of")
@Data
@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor

public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String type;
    private String url;
    private Integer expect;
    private Boolean proxy;
}
