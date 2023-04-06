package fr.univcotedazur.multiCredit.cli.model;

public class CliProduct {
    private long id;
    private int points;
    private  String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShop(CliShop shop) {
        this.shop = shop;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

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

    public void setId(Long id) {
        this.id = id;
    }


    public double getDiscountPercentage() {
        return discountPercentage;
    }
    @Override
    public String toString(){

        if(shop!=null)
            return "id : "+id+ " Name : "+name+" Price : "+price+" Points : "+points+" Discount : "+discountPercentage+" Shop : "+shop.toString();
        else
            return "id : "+id+ " Name : "+name+" Price : "+price+" Points : "+points+" Discount : "+discountPercentage;
    }
}
