package fr.univcotedazur.simpletcfs.entities;

import java.util.UUID;

public class DiscountedProduct extends Product{

    private Double DiscountPourcentage;
    public DiscountedProduct(Long id, String name, double price, int points, Double DiscountPourcentage) {
        super( name, price,points);
        this.DiscountPourcentage=DiscountPourcentage;
    }

    public Double getDiscountPourcentage() {
        return DiscountPourcentage;
    }

    public void setDiscountPourcentage(Double discountPourcentage) {
        DiscountPourcentage = discountPourcentage;
    }
}
