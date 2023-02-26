package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Purchase extends Transaction{
    @Getter
    @Setter
    public  int earnedPoints;

    @Setter
    @Getter
    public double totalPrice;
    @Getter
    @Setter
    public String creditCard;
    @Getter
    @Setter
    public List<Item> item;
    public Purchase(List<Item> items){
        this.item = items;
        this.earnedPoints = 0;
        this.totalPrice = 0;
        items.forEach(item -> {
            this.earnedPoints += item.getProduct().getPoints() * item.getAmount();
            this.totalPrice += item.getProduct().getPrice() * item.getAmount();
        });

    }
}
