package fr.univcotedazur.simpletcfs.entities;


import java.util.UUID;

public class Product {


    private String name;
    private UUID id;

    private double price;

    private int points;
    public Product(UUID id, String name, double price, int points) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
