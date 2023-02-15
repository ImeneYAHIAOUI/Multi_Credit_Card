package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;

import java.time.LocalTime;

public interface ShopRegistration {
    Shop addShop(String name, String address, LocalTime openingHour, LocalTime closingHour) throws MissingInformationException;
    void removeShop(Shop shop);
}
