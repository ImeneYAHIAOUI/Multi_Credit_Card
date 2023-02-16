package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface ShopRegistration {
    Shop addShop(String name, String address, Map<WeekDay, Planning> planning, List<Product> productList) throws MissingInformationException;    void removeShop(Shop shop);
}
