package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.WeekDay;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

public class ShopDTO {
    @NotBlank(message = "name should not be blank")
    private String name;
    @Pattern(regexp = "^(.+)@(.+)$", message = "address should be valid")
    private String address;
    private Map<WeekDay, Planning> planning;
    private List<Product> productList;
    private List<Gift> giftList;
    public ShopDTO(String name, String address, Map<WeekDay, Planning> planning, List<Product> productList, List<Gift> giftList) {
        this.name = name;
        this.address = address;
        this.planning = planning;
        this.productList = productList;
        this.giftList = giftList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<WeekDay, Planning> getPlanning() {
        return planning;
    }

    public void setPlanning(Map<WeekDay, Planning> planning) {
        this.planning = planning;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Gift> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }
}
