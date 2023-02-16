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
    public Shop(UUID id, String name, String address, Map<WeekDay, Planning> planning, List<Product> products) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.planning = planning;
        productList=products;
    }


}

