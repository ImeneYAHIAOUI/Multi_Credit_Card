package fr.univcotedazur.simpletcfs.entities;



import java.util.*;
import java.util.Map;
import java.util.UUID;

public class Shop {

    private UUID id;

    private String name;

    private String address;

    private Map<WeekDay, Planning> planning;

    public List<Product> productList;

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

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Map<WeekDay, Planning> getPlanning() {
        return planning;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Gift> getGiftList() {
        return giftList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPlanning(Map<WeekDay, Planning> planning) {
        this.planning = planning;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }
}

