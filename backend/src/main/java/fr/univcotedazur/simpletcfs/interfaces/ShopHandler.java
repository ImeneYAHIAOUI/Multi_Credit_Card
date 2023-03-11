package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;

import java.time.LocalTime;

public interface ShopHandler {
    void modifyAddress(Shop shop, String address);
    void modifyPlanning(Shop shop, WeekDay day, LocalTime OpeningHours, LocalTime ClosingHours);
    void addGift(Shop shop, Gift gift);
    void removeGift(Shop shop, Gift gift) throws GiftNotFoundException;
     void removeProduct(Shop shop, Product product)throws ProductNotFoundException;
     void addProduct(Shop shop, Product product);

}
