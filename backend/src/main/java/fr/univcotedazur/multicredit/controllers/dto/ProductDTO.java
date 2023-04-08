package fr.univcotedazur.multicredit.controllers.dto;

public class ProductDTO {
    private long id;
    private ShopDTO shop;
    private int points;
    private String name;
    private double price;
    private double discountPercentage;

    public ProductDTO() {
    }

    public ProductDTO(long id, ShopDTO shop, String name, int points, double price, double discountPercentage) {
        this.points = points;
        this.price = price;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.shop = shop;
        this.id = id;
    }

    public ProductDTO(String name, int points, double price, double discountPercentage) {
        this.points = points;
        this.price = price;
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    public long getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }
}
