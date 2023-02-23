package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class DiscountedProduct extends Product{
    @Getter
    @Setter
    private Double DiscountPourcentage;
    public DiscountedProduct(UUID id, String name, double price, Double DiscountPourcentage) {
        super(id, name, price,0);
        this.DiscountPourcentage=DiscountPourcentage;
    }
}
