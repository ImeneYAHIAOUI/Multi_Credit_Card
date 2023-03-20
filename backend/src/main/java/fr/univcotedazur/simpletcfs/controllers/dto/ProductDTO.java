package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Product;

public class ProductDTO {
    private Long id;
    private ShopDTO shop;
    private int points;
    private  String name;
    private double price;
    private double discountPercentage;
    public  ProductDTO(Long id ,ShopDTO shop, String name,int points, double price, double discountPercentage) {
        this.points = points;
        this.price = price;
        this.name=name;
        this.discountPercentage = discountPercentage;
        this.shop=shop;
        this.id=id;
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

    public double getDiscountPercentage() {
        return discountPercentage;
    }
}
