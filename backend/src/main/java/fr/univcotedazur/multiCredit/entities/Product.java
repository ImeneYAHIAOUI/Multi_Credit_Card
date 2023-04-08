package fr.univcotedazur.multiCredit.entities;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Product {
    @NotBlank
    private String name;
    @Id
    @GeneratedValue
    @Column(name = "Product_id", nullable = false)
    private Long id;
    private double price;
    @ManyToOne
    @JoinColumn(name = "Shop_id", nullable = false)
    private Shop shop;
    private int points;
    private double discountPercentage;

    public Product(String name, double price, int points, double percentage) {
        this.name = name;
        this.price = price;
        this.points = points;
        this.discountPercentage = percentage;
    }

    public Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price)
                && Objects.equals(points, product.points)
                && Objects.equals(discountPercentage, product.discountPercentage);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (shop != null ? shop.hashCode() : 0);
        result = 31 * result + points;
        temp = Double.doubleToLongBits(discountPercentage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price - (price * discountPercentage);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return " Product id : " + id + " Name : " + name + " Price : " + price + " Points : " + points + " Discount : " + discountPercentage;
    }
}
