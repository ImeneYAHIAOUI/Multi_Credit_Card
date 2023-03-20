package fr.univcotedazur.simpletcfs.cli.model;

public class CliProduct {
    private Long id;
    private int points;
    private  String name;
    private CliShop shop;
    private double price;
    private double discountPercentage;
    public CliProduct(String name,int points, double price, double discountPercentage) {
        this.points = points;
        this.price = price;
        this.name=name;
        this.discountPercentage = discountPercentage;
    }
    public CliShop getShop() {
        return shop;
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
    @Override
    public String toString(){
        return "Name : "+name+" Price : "+price+" Points : "+points+" Discount : "+discountPercentage;
    }
}
