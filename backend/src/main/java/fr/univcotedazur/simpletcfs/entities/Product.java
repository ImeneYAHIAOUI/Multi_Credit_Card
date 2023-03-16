package fr.univcotedazur.simpletcfs.entities;


import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {

    @NotBlank
    private String name;
    @Id
    @GeneratedValue
    @Column(name="Product_id", nullable=false)
    private Long id;
    private double price;
    @ManyToOne
    @JoinColumn(name="Shop_id", nullable=false)
    private Shop shop;
    private int points;
    public Product( String name, double price, int points) {

        this.name = name;
        this.price = price;
        this.points = points;
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
                 && Objects.equals(shop, product.shop);
    }
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
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
