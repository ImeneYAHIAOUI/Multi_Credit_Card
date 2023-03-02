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
    @Getter
    @Setter
    private int points;
    public Product(UUID id, String name, double price, int points) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.points = points;
    }


}
