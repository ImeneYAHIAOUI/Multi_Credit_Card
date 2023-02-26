package fr.univcotedazur.simpletcfs.entities;

import fr.univcotedazur.simpletcfs.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.Map;
import java.util.UUID;

public class Shop {
    @Getter
    private UUID id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private Map<WeekDay, Planning> planning;
    @Getter
    @Setter
    public List<Product> productList;
    @Getter
    @Setter
    public List<Gift> giftList;
    public Shop(UUID id, String name, String address, Map<WeekDay, Planning> planning, List<Product> products, List<Gift> gifts) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.planning = planning;
        productList=products;
        giftList=gifts;
    }
    public void addProduct(Product product){
        productList.add(product);
    }
    public void removeProduct(Product product){
        productList.remove(product);
    }
    public void addGift(Gift gift){
        giftList.add(gift);
    }

}

