package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

public class Item {
    @Getter
    @Setter
    public int amount;
    @Setter
    @Getter
    public Product product;
    public void Item(Product product, int amount){
        this.product = product;
        this.amount = amount;
    }
}
