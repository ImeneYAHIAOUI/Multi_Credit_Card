package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Product {

    @Getter
    @Setter
    private String name;
    @Getter
    private UUID id;
    @Getter
    @Setter
    private double price;
    public Product(UUID id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


}
